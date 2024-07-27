package com.frg.carservice.repository;

import com.frg.carservice.entities.Maintainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintainerRepository extends CrudRepository<Maintainer, Long> {}
