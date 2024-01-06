package br.com.joaogabriel.expenses.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UserPostRequest(
        @NotBlank
        String name,
        @NotBlank
        String username,
        @Email
        @NotBlank
        String email,
        @JsonProperty("password")
        String hashPassword
) implements Serializable {
}
