package TwitterApi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
        @NotNull Long tweetId,
        @NotNull Long userId,
        @NotBlank String commentDesc) {
}
