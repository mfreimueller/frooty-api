package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Week;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekRepository extends CrudRepository<Week, Integer> {
    @Query("SELECT w.id FROM Week w WHERE w.group_id = :groupId ORDER BY w.startDate ASC")
    List<Integer> findIdsByGroupId(@Param("groupId") Integer groupId);
}
