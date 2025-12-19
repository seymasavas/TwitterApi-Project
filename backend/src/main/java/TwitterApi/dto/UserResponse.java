package TwitterApi.dto;

import java.time.LocalDateTime;

public record UserResponse(
        long userId,
        String userName,
        String email,
        LocalDateTime registrationDate
) {
}
