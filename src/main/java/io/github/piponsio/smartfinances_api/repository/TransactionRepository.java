package io.github.piponsio.smartfinances_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.piponsio.smartfinances_api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByID(Long id);

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
}
