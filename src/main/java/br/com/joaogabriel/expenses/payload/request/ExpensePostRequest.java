package br.com.joaogabriel.expenses.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JsonPropertyOrder({"id", "name", "description", "paidIn", "amount"})
public record ExpensePostRequest(

        @NotBlank(message = "The field name cannot be empty.")
        @Size(min = 5, max = 100, message = "The field name must be between {min} and {max} characters.")
        String name,

        @NotBlank(message = "The field description cannot be empty.")
        String description,

        @NotNull
        BigDecimal amount,

        @NotNull
        @JsonProperty("Paid in")
        LocalDate paidIn
) {
}
