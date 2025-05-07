package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.PlanInvite;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlanInviteRepository extends CrudRepository<PlanInvite, Integer> {

    List<PlanInvite> findAllByPlan_Id(Integer planId);

}
