package br.com.joaogabriel.expenses.repository;

import br.com.joaogabriel.expenses.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;


public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    Expense findByDescriptionAndIsActive(String description, Boolean isActive);

    List<Expense> findByDescriptionContainingAndIsActive(String description, Boolean isActive);

    Expense findByNameAndIsActive(String name, Boolean isActive);

    List<Expense> findByNameContainingAndIsActive(String name, Boolean isActive);
}
