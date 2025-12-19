package TwitterApi.dto;

import java.time.LocalDateTime;

public record RetweetResponse(long retweetId, long userId, long tweetId, LocalDateTime retweetTime) {
}
