package io.github.piponsio.smartfinances_api.dto.request;

import io.github.piponsio.smartfinances_api.enums.type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    String categoryName;
    type type;
}
