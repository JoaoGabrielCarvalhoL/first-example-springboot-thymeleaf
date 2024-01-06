package br.com.joaogabriel.expenses.service;

import br.com.joaogabriel.expenses.payload.request.UserPostRequest;
import br.com.joaogabriel.expenses.payload.response.UserGetResponse;

public interface AuthenticationService {

    public UserGetResponse register(final UserPostRequest request);
}
