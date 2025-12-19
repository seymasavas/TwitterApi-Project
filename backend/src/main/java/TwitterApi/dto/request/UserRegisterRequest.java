package TwitterApi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @NotBlank String userName,
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
