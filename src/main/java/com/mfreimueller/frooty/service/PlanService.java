package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Plan;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.*;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.PlanRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ConversionService conversionService;

    public List<PlanDto> getAllPlansOfCurrentUser() {
        final User currentUser = userService.getCurrentUser();

        return currentUser.getPlans()
                .stream()
                .map(p -> conversionService.convert(p, PlanDto.class))
                .toList();
    }

    public PlanDto createPlan(CreateUpdatePlanDto createUpdatePlanDto) {
        Assert.notNull(createUpdatePlanDto, "createUpdatePlanDto must not be null.");

        final User currentUser = userService.getCurrentUser();
        final Plan plan = planRepository.save(new Plan(createUpdatePlanDto.name(), currentUser));

        return conversionService.convert(plan, PlanDto.class);
    }

    public Plan findPlan(Integer id) {
        Assert.notNull(id, "id must not be null.");

        final User currentUser = userService.getCurrentUser();
        if (!planRepository.isUserAuthorizedForPlan(id, currentUser.getId())) {
            throw new EntityNotFoundException(id);
        }

        return planRepository.findById(id).orElseThrow();
    }

    public PlanDto updatePlan(Integer id, CreateUpdatePlanDto createUpdatePlanDto) {
        Assert.notNull(id, "id must not be null.");
        Assert.notNull(createUpdatePlanDto, "updatePlanDto must not be null.");

        return planRepository.findById(id)
                .map(p -> {
                    p.setName(createUpdatePlanDto.name());
                    return conversionService.convert(planRepository.save(p), PlanDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public void deletePlanIfOwnerOrRemoveMembership(Integer planId) {
        Assert.notNull(planId, "planId must not be null.");

        final User currentUser = userService.getCurrentUser();
        final boolean userIsOwner = planRepository.existsByIdAndOwner_Id(planId, currentUser.getId());

        if (userIsOwner) {
            planRepository.deleteById(planId);
        } else {
            Plan plan = planRepository.findById(planId).orElseThrow();
            Set<User> users = plan.getUsers();
            users.remove(currentUser);

            planRepository.save(plan);
        }
    }

    public void addUserToPlan(Plan plan, User user) {
        Set<User> users = plan.getUsers();
        users.add(user);

        plan.setUsers(users);
        planRepository.save(plan);
    }
}
