package io.github.piponsio.smartfinances_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.github.piponsio.smartfinances_api.enums.type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDto {
    private Long id;
    private BigDecimal amount;
    private String description;
    private type type;
    private String categoryName;
    private LocalDateTime date;
}
