package io.github.piponsio.smartfinances_api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
}
