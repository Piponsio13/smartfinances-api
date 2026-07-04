package io.github.piponsio.smartfinances_api.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.github.piponsio.smartfinances_api.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequestDto {
    private BigDecimal amount;
    private String description;
    private TransactionType type;
    private Long categoryId;
    private LocalDateTime date;
}