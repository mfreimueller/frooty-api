package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Week;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeekRepository extends CrudRepository<Week, Integer> {
    List<Week> findAllByPlanId(Integer planId, Pageable pageable);

    boolean existsByPlan_IdAndStartDate(Integer planId, LocalDate startDate);
}
