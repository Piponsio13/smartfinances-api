package io.github.piponsio.smartfinances_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.piponsio.smartfinances_api.dto.request.TransactionRequestDto;
import io.github.piponsio.smartfinances_api.dto.response.TransactionResponseDto;
import io.github.piponsio.smartfinances_api.service.transaction.TransactionService;
import io.github.piponsio.smartfinances_api.utils.CustomResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<CustomResponse<Void>> createTransaction(@RequestBody TransactionRequestDto request) {
        transactionService.createTransaction(request);
        CustomResponse<Void> customResponse = CustomResponse.<Void>builder()
                .data(null)
                .message("Transaction created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(customResponse);
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<TransactionResponseDto>>> getAllTransactions() {
        List<TransactionResponseDto> transactions = transactionService.getAllTransactions();

        HttpStatus status = transactions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        String message = transactions.isEmpty()
                ? "User does not have transactions"
                : "All user transactions retrieved successfully";

        CustomResponse<List<TransactionResponseDto>> customResponse = CustomResponse
                .<List<TransactionResponseDto>>builder()
                .data(transactions)
                .message(message)
                .statusCode(status.value())
                .build();

        return ResponseEntity.status(status).body(customResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<TransactionResponseDto>> getTransactionById(@PathVariable Long id) {
        TransactionResponseDto transaction = transactionService.getTransaction(id);

        CustomResponse<TransactionResponseDto> customResponse = CustomResponse.<TransactionResponseDto>builder()
                .data(transaction)
                .message("Transaction retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(customResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequestDto request) {
        transactionService.updateTransaction(id, request);

        CustomResponse<Void> customResponse = CustomResponse.<Void>builder()
                .data(null)
                .message("Transaction updated successfully")
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(customResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);

        CustomResponse<Void> customResponse = CustomResponse.<Void>builder()
                .data(null)
                .message("Transaction deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(customResponse);
    }
}
