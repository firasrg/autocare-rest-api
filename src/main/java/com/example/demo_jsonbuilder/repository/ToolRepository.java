package com.example.demo_jsonbuilder.repository;

import com.example.demo_jsonbuilder.entities.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends CrudRepository<Tool, Long> {
    @Query("SELECT t FROM Tool t WHERE t.maintainer.id = :maintainerId")
    List<Tool> findToolsByMaintainerId(@Param("maintainerId") Long maintainerId);
}
