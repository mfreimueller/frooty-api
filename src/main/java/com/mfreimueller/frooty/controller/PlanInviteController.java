package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.domain.PlanInvite;
import com.mfreimueller.frooty.dto.*;
import com.mfreimueller.frooty.service.PlanInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan/{planId}/invite")
public class PlanInviteController {
    @Autowired
    private PlanInviteService planInviteService;

    @GetMapping
    @CheckPlanAccess(planIdParam = "planId", asOwner = true)
    public List<PlanInviteDto> findAll(@PathVariable Integer planId) {
        return planInviteService.getAllInvitesForPlan(planId);
    }

    @PostMapping
    @CheckPlanAccess(planIdParam = "planId", asOwner = true)
    public PlanInviteDto inviteUser(@PathVariable Integer planId, @RequestBody CreatePlanInviteDto createPlanInviteDto) {
        return planInviteService.inviteUserToPlan(planId, createPlanInviteDto);
    }

    @PostMapping("/{inviteId}/accept")
    @CheckPlanAccess(planIdParam = "planId")
    public PlanInviteDto acceptInvitation(@PathVariable Integer planId, @PathVariable Integer inviteId) {
        return planInviteService.setInvitationStatus(inviteId, PlanInvite.Status.ACCEPTED);
    }

    @PostMapping("/{inviteId}/decline")
    @CheckPlanAccess(planIdParam = "planId")
    public PlanInviteDto declineInvitation(@PathVariable Integer planId, @PathVariable Integer inviteId) {
        return planInviteService.setInvitationStatus(inviteId, PlanInvite.Status.DECLINED);
    }

    @DeleteMapping("/{inviteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckPlanAccess(planIdParam = "planId", asOwner = true)
    public void deleteOne(@PathVariable Integer planId, @PathVariable Integer inviteId) {
        planInviteService.deleteInvite(inviteId);
    }
}
