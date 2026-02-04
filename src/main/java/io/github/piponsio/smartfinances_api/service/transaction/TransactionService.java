package io.github.piponsio.smartfinances_api.service.transaction;

import java.util.List;

import io.github.piponsio.smartfinances_api.dto.request.TransactionFilterDto;
import io.github.piponsio.smartfinances_api.dto.request.TransactionRequestDto;
import io.github.piponsio.smartfinances_api.dto.response.TransactionResponseDto;
import io.github.piponsio.smartfinances_api.dto.response.TransactionSummaryDto;

public interface TransactionService {
    void createTransaction(TransactionRequestDto request);

    List<TransactionResponseDto> getAllTransactions(TransactionFilterDto filterDto);

    TransactionResponseDto getTransaction(Long id);

    void updateTransaction(Long id, TransactionRequestDto request);

    void deleteTransaction(Long id);

    TransactionSummaryDto getSummary(int month, int year);
}
