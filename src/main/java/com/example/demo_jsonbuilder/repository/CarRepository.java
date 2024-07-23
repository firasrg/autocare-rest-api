package com.example.demo_jsonbuilder.repository;

import com.example.demo_jsonbuilder.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {}
