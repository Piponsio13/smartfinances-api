package io.github.piponsio.smartfinances_api.dto.request;

import io.github.piponsio.smartfinances_api.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    String categoryName;
    TransactionType type;
}
