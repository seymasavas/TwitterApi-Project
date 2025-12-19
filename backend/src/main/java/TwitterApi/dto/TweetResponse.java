package TwitterApi.dto;

import java.time.LocalDateTime;

public record TweetResponse (long userId, long tweetId, String tweetDesc, LocalDateTime tweetTime){
}
