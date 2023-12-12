package br.com.joaogabriel.expenses.mapper;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import br.com.joaogabriel.expenses.payload.request.ExpensePutRequest;
import br.com.joaogabriel.expenses.payload.response.ExpenseGetResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    Expense toExpense(ExpensePostRequest expensePostRequest);

    Expense toExpense(ExpensePutRequest expensePutRequest);

    ExpenseGetResponse toExpenseGetResponse(Expense expense);
}
