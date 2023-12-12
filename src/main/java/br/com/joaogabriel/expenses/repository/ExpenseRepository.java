package br.com.joaogabriel.expenses.repository;

import br.com.joaogabriel.expenses.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    Expense findByDescriptionAndIsActive(String description, Boolean isActive);

    Expense findByNameAndIsActive(String name, Boolean isActive);
}
