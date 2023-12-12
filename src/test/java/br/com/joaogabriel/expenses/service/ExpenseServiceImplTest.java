package br.com.joaogabriel.expenses.service;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.entity.ExpenseBuilder;
import br.com.joaogabriel.expenses.exception.ResourceNotFoundException;
import br.com.joaogabriel.expenses.mapper.ExpenseMapper;
import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import br.com.joaogabriel.expenses.payload.request.ExpensePutRequest;
import br.com.joaogabriel.expenses.payload.response.ExpenseGetResponse;
import br.com.joaogabriel.expenses.repository.ExpenseRepository;
import br.com.joaogabriel.expenses.service.impl.ExpenseServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private ExpenseMapper expenseMapper;
    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;
    private ExpenseGetResponse getResponse;
    private ExpensePostRequest postRequest;
    private ExpensePutRequest putRequest;
    private UUID id;


    @BeforeEach
    public void setup() {
        this.id = UUID.randomUUID();

        this.getResponse = new ExpenseGetResponse(id, "Expense Test",
                "Description Test", new BigDecimal("100.00"), LocalDate.now());

        this.postRequest = new ExpensePostRequest("Expense Test",
                "Description Test", new BigDecimal("100.00"), LocalDate.now());

        this.putRequest = new ExpensePutRequest(id, "Expense Test",
                "Description Test", new BigDecimal("100.00"), LocalDate.now());

        this.expense = ExpenseBuilder.builder()
                .id(id)
                .name("Expense Test")
                .description("Description Test")
                .amount(new BigDecimal("100.00"))
                .paidIn(LocalDate.now())
                .build();
    }

    @DisplayName("Given expense post request object, when save expense, then return expense get response.")
    @Test
    public void givenExpensePostRequest_whenSaveExpense_thenReturnExpenseGetResponse() {
        BDDMockito.given(this.expenseMapper.toExpense(postRequest))
                .willReturn(this.expense);
        BDDMockito.given(this.expenseRepository.save(expense))
                .willReturn(this.expense);
        BDDMockito.given(this.expenseMapper.toExpenseGetResponse(expense)).willReturn(this.getResponse);
        ExpenseGetResponse saved = expenseService.save(postRequest);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.id()).isEqualTo(id);
    }

    @DisplayName("Given existing expense id from database, when retrieve expense by id, " +
            "then return expense object persisted.")
    @Test
    public void givenExistingExpenseId_whenFindExpenseById_thenReturnExpensePersisted() {
        BDDMockito.given(this.expenseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(Optional.of(this.expense));
        BDDMockito.given(this.expenseMapper.toExpenseGetResponse(ArgumentMatchers.any(Expense.class)))
                .willReturn(this.getResponse);
        ExpenseGetResponse response = expenseService.findById(id);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.id()).isEqualTo(getResponse.id());
    }

    @DisplayName("Given invalid expense id, when find expense id, then throw resource not found exception.")
    @Test
    public void givenInvalidExpenseId_whenFindExpenseById_thenThrowResourceNotFoundException() {
        BDDMockito.given(this.expenseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willThrow(new ResourceNotFoundException("Expense not found into database. Id: " + id));
        Throwable throwable = Assertions.catchThrowable(() -> this.expenseService.findById(id));
        Assertions.assertThat(throwable.getMessage()).isEqualTo("Expense not found into database. Id: " + id);
        Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("Given existing expense id from database, when retrieve entity by id, " +
            "then return expense object persisted.")
    @Test
    public void givenExistingExpenseId_whenFindEntityById_thenReturnExpensePersisted() {
        BDDMockito.given(this.expenseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(Optional.of(this.expense));
        Expense response = expenseService.findEntityById(id);
        Assertions.assertThat(response).isNotNull();
        System.out.println(response);
        Assertions.assertThat(response.getId()).isEqualTo(id);
    }

    @DisplayName("Given invalid expense id from database, when retrieve entity by id, " +
            "then return expense object persisted.")
    @Test
    public void givenInvalidExpenseId_whenFindEntityById_thenThrowResourceNotFoundException() {
        BDDMockito.given(this.expenseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willThrow(new ResourceNotFoundException("Expense not found into database. Id: " + id));
        Throwable throwable = Assertions.catchThrowable(() -> this.expenseService.findById(id));
        Assertions.assertThat(throwable.getMessage()).isEqualTo("Expense not found into database. Id: " + id);
        Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("Given expense object, when update expense, then return expense updated from " +
            "database.")
    @Test
    public void givenExpenseObject_whenUpdateExpense_thenReturnExpenseUpdated() {
        BDDMockito.given(this.expenseRepository.save(ArgumentMatchers.any(Expense.class)))
                .willReturn(this.expense);
        BDDMockito.given(this.expenseMapper.toExpense(putRequest))
                .willReturn(this.expense);
        BDDMockito.given(this.expenseMapper.toExpenseGetResponse(ArgumentMatchers.any(Expense.class)))
                .willReturn(this.getResponse);
        ExpenseGetResponse updated = this.expenseService.update(putRequest);
        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.id()).isEqualTo(getResponse.id());
    }

    @DisplayName("Given existing expense name, when retrieve expense by name, then return " +
            "expense object persisted from database")
    @Test
    public void givenExistingExpenseName_whenRetrieveExpenseByName_thenReturnExpenseObject() {
        BDDMockito.given(this.expenseRepository.findByNameAndIsActive(this.expense.getName(), true))
                .willReturn(this.expense);
        BDDMockito.given(this.expenseMapper.toExpenseGetResponse(ArgumentMatchers.any(Expense.class)))
                .willReturn(this.getResponse);

        ExpenseGetResponse response = this.expenseService.findByName(this.expense.getName());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.name()).isEqualTo(expense.getName());
    }


    @DisplayName("Given existing expense name, when retrieve expense by name, then throw " +
            "resource not found exception")
    @Test
    public void givenInvalidExpenseName_whenRetrieveExpenseByName_thenThrowResourceNotFoundException() {
        BDDMockito.given(this.expenseRepository.findByNameAndIsActive(this.expense.getName(), true))
                .willThrow(new ResourceNotFoundException("Expense not found into database. Name: " + expense.getName()));
        Throwable throwable = Assertions.catchThrowable(() -> this.expenseService.findByName(this.expense.getName()));
        Assertions.assertThat(throwable.getMessage()).isEqualTo("Expense not found into database. Name: " + expense.getName());
        Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
    }


    @DisplayName("Given existing expense description, when retrieve expense by description, then return " +
            "expense object persisted from database")
    @Test
    public void givenExistingExpenseDescription_whenRetrieveExpenseByName_thenReturnExpenseObject() {
        BDDMockito.given(this.expenseRepository.findByDescriptionAndIsActive(this.expense.getDescription(), true))
                .willReturn(this.expense);
        BDDMockito.given(this.expenseMapper.toExpenseGetResponse(ArgumentMatchers.any(Expense.class)))
                .willReturn(this.getResponse);

        ExpenseGetResponse response = this.expenseService.findByDescription(this.expense.getDescription());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.description()).isEqualTo(expense.getDescription());
    }

    @DisplayName("Given existing expense name, when retrieve expense by name, then throw " +
            "resource not found exception")
    @Test
    public void givenInvalidExpenseDescription_whenRetrieveExpenseByName_thenThrowResourceNotFoundException() {
        BDDMockito.given(this.expenseRepository.findByDescriptionAndIsActive(this.expense.getDescription(), true))
                .willThrow(new ResourceNotFoundException("Expense not found into database. Description: " + expense.getDescription()));
        Throwable throwable = Assertions.catchThrowable(() -> this.expenseService.findByDescription(this.expense.getDescription()));
        Assertions.assertThat(throwable.getMessage()).isEqualTo("Expense not found into database. Description: " + expense.getDescription());
        Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
    }

    @DisplayName("Given expense page, when retrieve all expenses, then return expenses with pagination")
    @Test
    public void givenExpensePage_whenRetrieveAllExpenses_thenReturnPageExpense() {
        PageImpl<Expense> page = new PageImpl<>(Collections.emptyList(),
                PageRequest.of(0, 10, Sort.Direction.ASC, "name"), 0);
        BDDMockito.given(this.expenseRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .willReturn(page);
        Page<ExpenseGetResponse> response =
                this.expenseService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "name"));
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getSize()).isEqualTo(page.getSize());
    }

    @DisplayName("Given expense list, when retrieve all expenses, then return list of expenses")
    @Test
    public void givenExpenseList_whenRetrieveAllExpenses_thenReturnListOfExpenses() {
        BDDMockito.given(this.expenseRepository.findAll())
                .willReturn(Collections.emptyList());
        List<ExpenseGetResponse> response = this.expenseService.findAll();
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.size()).isEqualTo(0);
    }

    @DisplayName("Given existing expense id, when delete expense by id, then nothing")
    @Test
    public void givenExistingExpenseId_whenDeleteExpenseById_thenNothing() {
        BDDMockito.given(this.expenseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(Optional.of(this.expense));
        this.expenseService.delete(id);

        BDDMockito.verify(expenseRepository, Mockito.never()).delete(this.expense);
    }

    @DisplayName("Given existing expense id, when delete expense by id, then throw " +
            "resource not found exception")
    @Test
    public void givenInvalidExpenseId_whenDeleteExpenseById_thenThrowResourceNotFoundException() {
        BDDMockito.given(this.expenseRepository.findById(ArgumentMatchers.any(UUID.class)))
                .willThrow(new ResourceNotFoundException("Expense not found into database. Id: " + id));

        Throwable throwable = Assertions.catchThrowable(() -> this.expenseService.delete(id));
        Assertions.assertThat(throwable.getMessage()).isEqualTo("Expense not found into database. Id: " + id);
        Assertions.assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
    }
}
