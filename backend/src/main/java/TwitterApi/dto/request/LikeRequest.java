package TwitterApi.dto.request;

import jakarta.validation.constraints.NotNull;

public record LikeRequest(
        @NotNull Long tweetId,
        @NotNull Long userId
) {
}
