package com.mfreimueller.frooty.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UpdateUserDto(@NotEmpty String username, @NotEmpty @Email String email, String password) {}
