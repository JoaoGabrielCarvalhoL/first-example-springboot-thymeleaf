package br.com.joaogabriel.expenses.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseGetResponse(
        UUID id,

        String name,

        String description,

        BigDecimal amount,

        @JsonProperty("Paid in")
        LocalDate paidIn
) implements Serializable {
}
