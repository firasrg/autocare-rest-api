package com.frg.carservice.repository;

import com.frg.carservice.entities.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepository extends CrudRepository<Tool, Long> {
    @Query("SELECT t FROM Tool t WHERE t.maintainer.id = :maintainerId")
    List<Tool> findToolsByMaintainerId(@Param("maintainerId") Long maintainerId);
}
