package com.mfreimueller.frooty.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUserDto(@NotEmpty String username, @NotEmpty @Email String email, @NotEmpty @Size(min = 8) String password) {}
