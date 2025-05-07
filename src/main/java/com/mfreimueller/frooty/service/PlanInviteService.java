package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Plan;
import com.mfreimueller.frooty.domain.PlanInvite;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.CreatePlanInviteDto;
import com.mfreimueller.frooty.dto.PlanInviteDto;
import com.mfreimueller.frooty.exception.PermissionDeniedException;
import com.mfreimueller.frooty.repositories.PlanInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlanInviteService {
    @Autowired
    private PlanInviteRepository planInviteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Autowired
    private ConversionService conversionService;

    public List<PlanInviteDto> getAllInvitesForPlan(Integer planId) {
        Assert.notNull(planId, "planId must not be null.");

        return planInviteRepository.findAllByPlan_Id(planId)
                .stream()
                .map(i -> conversionService.convert(i, PlanInviteDto.class))
                .toList();
    }

    public PlanInviteDto inviteUserToPlan(Integer planId, CreatePlanInviteDto createPlanInviteDto) {
        Assert.notNull(planId, "planId must not be null.");
        Assert.notNull(createPlanInviteDto, "createPlanInviteDto must not be null.");

        final Plan plan = planService.findPlan(planId);
        final User invitee = userService.getUserById(createPlanInviteDto.invitedUserId());

        final PlanInvite planInvite = new PlanInvite(plan, invitee, LocalDate.now());
        return conversionService.convert(
                planInviteRepository.save(planInvite),
                PlanInviteDto.class
        );
    }

    public PlanInviteDto setInvitationStatus(Integer inviteId, PlanInvite.Status status) {
        Assert.notNull(inviteId, "inviteId must not be null.");

        final User currentUser = userService.getCurrentUser();

        PlanInvite planInvite = planInviteRepository.findById(inviteId).orElseThrow();

        // only the invited user can accept or decline an invitation
        if (!planInvite.getInvitedUser().equals(currentUser)) {
            throw new PermissionDeniedException();
        }

        planInvite.setStatus(status);
        planInvite.setUpdatedAt(LocalDate.now());

        if (status == PlanInvite.Status.ACCEPTED) {
            planService.addUserToPlan(planInvite.getPlan(), planInvite.getInvitedUser());
        }

        return conversionService.convert(
                planInviteRepository.save(planInvite),
                PlanInviteDto.class
        );
    }

    public void deleteInvite(Integer inviteId) {
        Assert.notNull(inviteId, "inviteId must not be null.");
        planInviteRepository.deleteById(inviteId);
    }
}
