package br.com.joaogabriel.expenses.mapper;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.entity.User;
import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import br.com.joaogabriel.expenses.payload.request.ExpensePutRequest;
import br.com.joaogabriel.expenses.payload.request.UserPostRequest;
import br.com.joaogabriel.expenses.payload.response.ExpenseGetResponse;
import br.com.joaogabriel.expenses.payload.response.UserGetResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserPostRequest request);

    UserGetResponse toUserGetResponse(User user);
}
