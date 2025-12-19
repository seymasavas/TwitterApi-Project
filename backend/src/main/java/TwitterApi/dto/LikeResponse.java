package TwitterApi.dto;

import java.time.LocalDateTime;

public record LikeResponse(long likeId,long tweetId, long userId, LocalDateTime createdAt) {
}
