package io.github.piponsio.smartfinances_api.service.transaction;

import java.util.List;

import io.github.piponsio.smartfinances_api.dto.request.TransactionRequestDto;
import io.github.piponsio.smartfinances_api.dto.response.TransactionResponseDto;

public interface TransactionService {
    void createTransaction(TransactionRequestDto request);

    List<TransactionResponseDto> getAllTransactions();

    TransactionResponseDto getTransaction(Long id);

    void updateTransaction(Long id, TransactionRequestDto request);

    void deleteTransaction(Long id);
}
