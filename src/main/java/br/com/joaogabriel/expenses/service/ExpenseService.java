package br.com.joaogabriel.expenses.service;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import br.com.joaogabriel.expenses.payload.request.ExpensePutRequest;
import br.com.joaogabriel.expenses.payload.response.ExpenseGetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ExpenseGetResponse findById(final UUID id);

    Expense findEntityById(final UUID id);

    ExpenseGetResponse save(final ExpensePostRequest expensePostRequest);

    ExpenseGetResponse update(final ExpensePutRequest expensePutRequest);

    ExpenseGetResponse findByName(final String name);

    ExpenseGetResponse findByDescription(final String description);

    Page<ExpenseGetResponse> findAll(Pageable pageable);

    List<ExpenseGetResponse> findAll();

    void delete(UUID id);
}
