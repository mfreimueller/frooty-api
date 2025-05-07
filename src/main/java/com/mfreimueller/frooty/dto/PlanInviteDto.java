package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.PlanInvite;

import java.time.LocalDate;

public record PlanInviteDto(Integer id, Integer planId, Integer invitedUserId, PlanInvite.Status status, LocalDate sentAt, LocalDate updatedAt) {
}
