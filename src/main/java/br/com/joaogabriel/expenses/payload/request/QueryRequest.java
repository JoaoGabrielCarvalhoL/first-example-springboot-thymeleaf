package br.com.joaogabriel.expenses.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record QueryRequest(
        @NotBlank(message = "The field value cannot be empty!")
        String value
) implements Serializable {
}
