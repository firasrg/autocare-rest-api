package com.example.demo_jsonbuilder.repository;

import com.example.demo_jsonbuilder.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
