package io.github.piponsio.smartfinances_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.piponsio.smartfinances_api.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
}
