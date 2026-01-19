package io.github.piponsio.smartfinances_api.dto.response;

import io.github.piponsio.smartfinances_api.enums.type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {
    String name;
    type type;
}
