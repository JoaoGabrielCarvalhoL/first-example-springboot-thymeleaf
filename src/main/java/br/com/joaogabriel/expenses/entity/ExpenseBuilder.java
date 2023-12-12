package br.com.joaogabriel.expenses.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseBuilder {
    private final Expense expense;

    public ExpenseBuilder() {
        this.expense = new Expense();
    }

    public static ExpenseBuilder builder() {
        return new ExpenseBuilder();
    }

    public ExpenseBuilder id(UUID id) {
        this.expense.setId(id);
        return this;
    }

    public ExpenseBuilder name(String name) {
        this.expense.setName(name);
        return this;
    }

    public ExpenseBuilder description(String description) {
        this.expense.setDescription(description);
        return this;
    }

    public ExpenseBuilder amount(BigDecimal amount) {
        this.expense.setAmount(amount);
        return this;
    }

    public ExpenseBuilder paidIn(LocalDate date) {
        this.expense.setPaidIn(date);
        return this;
    }

    public Expense build() {
        return this.expense;
    }



}
