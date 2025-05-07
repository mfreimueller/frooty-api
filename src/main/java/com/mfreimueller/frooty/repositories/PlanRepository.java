package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Plan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlanRepository extends CrudRepository<Plan, Integer> {
    @Query("""
    SELECT COUNT(p) > 0
    FROM Plan p
    WHERE p.id = :planId
      AND (:userId = p.owner.id OR :userId IN (SELECT u.id FROM p.users u))
    """)
    boolean isUserAuthorizedForPlan(@Param("planId") Integer planId, @Param("userId") Integer userId);

    boolean existsByIdAndOwner_Id(Integer planId, Integer ownerId);
}
