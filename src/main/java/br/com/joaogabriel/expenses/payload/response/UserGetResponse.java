package br.com.joaogabriel.expenses.payload.response;

import java.io.Serializable;

public record UserGetResponse(
        String name,
        String username,
        String email
) implements Serializable {
}
