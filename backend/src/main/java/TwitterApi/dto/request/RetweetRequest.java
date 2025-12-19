package TwitterApi.dto.request;

import jakarta.validation.constraints.NotNull;

public record RetweetRequest(
        @NotNull Long tweetId,
        @NotNull Long userId
) {}
