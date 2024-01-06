package br.com.joaogabriel.expenses.repository;

import br.com.joaogabriel.expenses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
