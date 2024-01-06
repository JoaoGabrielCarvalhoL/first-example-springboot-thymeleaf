package br.com.joaogabriel.expenses.init;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.entity.ExpenseBuilder;
import br.com.joaogabriel.expenses.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ExpenseDataInitialization implements CommandLineRunner {

    private final ExpenseRepository expenseRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ExpenseDataInitialization(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Add Expenses");
        Expense expense = ExpenseBuilder.builder()
                .name("Expense Test")
                .description("Expense Description")
                .amount(new BigDecimal("100.00"))
                .paidIn(LocalDate.now())
                .build();

        Expense anotherExpense = ExpenseBuilder.builder()
                .name("Expense Test Test")
                .description("Expense Description Description")
                .amount(new BigDecimal("100.00"))
                .paidIn(LocalDate.now())
                .build();
        this.expenseRepository.save(expense);
        this.expenseRepository.save(anotherExpense);
    }
}
