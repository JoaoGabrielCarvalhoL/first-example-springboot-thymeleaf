package br.com.joaogabriel.expenses.repository;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.entity.ExpenseBuilder;
import br.com.joaogabriel.expenses.mapper.ExpenseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@DataJpaTest
public class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense expense;

    @BeforeEach
    public void setup() {
        this.expense = ExpenseBuilder.builder()
                .name("Expense Test")
                .description("Expense Description")
                .amount(new BigDecimal("100.00"))
                .paidIn(LocalDate.now())
                .build();
    }

    @DisplayName("Given expense object, when save expense, then return expense object persisted from database.")
    @Test
    public void givenExpenseObject_whenSaveExpense_thenReturnExpenseObjectPersisted() {
        Expense saved = this.expenseRepository.save(this.expense);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getName()).isEqualTo(expense.getName());
    }

    @DisplayName("Given expense list, when retrieve all expense, then return all expenses from database")
    @Test
    public void givenExpenseList_whenRetrieveAllExpenses_thenReturnAllExpenses() {
        this.expenseRepository.save(this.expense);
        List<Expense> saved = this.expenseRepository.findAll();
        Assertions.assertThat(saved.size()).isEqualTo(1);
        Assertions.assertThat(saved.get(0).getName()).isEqualTo(this.expense.getName());
    }

    @DisplayName("Given name of expense valid, when retrieve expense by name, then return expense object persisted " +
            "from database")
    @Test
    public void givenNameOfExpenseValid_whenRetrieveExpenseByName_thenReturnExpenseObjectPersisted() {
        this.expenseRepository.save(this.expense);
        Expense result = this.expenseRepository.findByNameAndIsActive(this.expense.getName(), true);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(this.expense.getName());
    }

    @DisplayName("Given description of expense valid, when retrieve expense by description, then return expense " +
            "object persisted from database")
    @Test
    public void givenDescriptionOfExpenseValid_whenRetrieveExpenseByDescription_thenReturnExpenseObjectPersisted() {
        this.expenseRepository.save(this.expense);
        Expense result = this.expenseRepository.findByDescriptionAndIsActive(this.expense.getDescription(), true);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getDescription()).isEqualTo(this.expense.getDescription());
    }

    @DisplayName("Given id of expense valid, when delete expense by id, then nothing")
    @Test
    public void givenExpenseObjectPersisted_whenDeleteExpense_thenNothing() {
        Expense saved = this.expenseRepository.save(this.expense);
        this.expenseRepository.delete(saved);
    }
}
