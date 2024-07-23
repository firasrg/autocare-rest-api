package com.example.demo_jsonbuilder.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Maintainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "maintainer")
    private List<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maintainer that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(cars, that.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cars);
    }
}
