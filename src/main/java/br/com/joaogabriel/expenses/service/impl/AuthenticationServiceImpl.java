package br.com.joaogabriel.expenses.service.impl;

import br.com.joaogabriel.expenses.entity.User;
import br.com.joaogabriel.expenses.mapper.UserMapper;
import br.com.joaogabriel.expenses.payload.request.UserPostRequest;
import br.com.joaogabriel.expenses.payload.response.UserGetResponse;
import br.com.joaogabriel.expenses.repository.UserRepository;
import br.com.joaogabriel.expenses.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserGetResponse register(UserPostRequest request) {
        logger.info("Saving user into database, {}", request);
        User saved = userRepository.save(userMapper.toUser(request));
        return userMapper.toUserGetResponse(saved);
    }
}
