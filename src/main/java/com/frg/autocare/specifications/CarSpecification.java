
package com.frg.autocare.specifications;

import com.frg.autocare.entities.Car;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Join;

public class CarSpecification {

  private static Specification<Car> hasAttribute(
          String value,
          String attribute,
          boolean isJoin,
          String joinField) {

    return (root, query, cb) -> {
      if (!StringUtils.hasText(value)) {
        return cb.conjunction();
      }

      if (isJoin) {
        Join<Object, Object> join = root.join(joinField);
        return cb.like(cb.lower(join.get(attribute)), "%" + value.toLowerCase() + "%");
      }

      return cb.like(cb.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
    };
  }

  public static Specification<Car> withFilters(
          String make, String model, String owner, String maintainer) {
    return Specification
            .where(hasAttribute(make, "make", false, null))
            .and(hasAttribute(model, "model", false, null))
            .and(hasAttribute(owner, "name", true, "customer"))
            .and(hasAttribute(maintainer, "name", true, "maintainer"));
  }
}