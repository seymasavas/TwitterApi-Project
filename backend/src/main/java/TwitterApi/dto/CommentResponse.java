package TwitterApi.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        String commentDesc,
        Long userId,
        Long tweetId,
        LocalDateTime commentTime
) {}