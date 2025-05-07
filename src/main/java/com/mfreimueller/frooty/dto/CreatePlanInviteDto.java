package com.mfreimueller.frooty.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePlanInviteDto(@NotNull Integer invitedUserId) {
}
