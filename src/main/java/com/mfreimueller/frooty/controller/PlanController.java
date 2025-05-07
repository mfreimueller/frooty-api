package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.dto.CreateUpdatePlanDto;
import com.mfreimueller.frooty.dto.PlanDto;
import com.mfreimueller.frooty.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping
    public List<PlanDto> findAll() {
        return planService.getAllPlansOfCurrentUser();
    }

    @PostMapping
    public PlanDto createOne(@RequestBody CreateUpdatePlanDto createUpdatePlanDto) {
        return planService.createPlan(createUpdatePlanDto);
    }

    @PutMapping("/{id}")
    @CheckPlanAccess(planIdParam = "id", asOwner = true)
    public PlanDto updateOne(@PathVariable Integer id, @RequestBody CreateUpdatePlanDto createUpdatePlanDto) {
        return planService.updatePlan(id, createUpdatePlanDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckPlanAccess(planIdParam = "id")
    public void deletePlanIfOwnerOrRemoveMembership(@PathVariable Integer id) {
        planService.deletePlanIfOwnerOrRemoveMembership(id);
    }
}
