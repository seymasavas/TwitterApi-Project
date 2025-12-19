package TwitterApi.Service;

import TwitterApi.entity.Tweet;
import java.util.List;

public interface TweetService {
    Tweet newTweet(long userId,String tweetDesc);
    List<Tweet> getTweetsByUserId(long userId);
    Tweet getTweetById (long tweetId);
    Tweet updateTweet(long tweetId, long userId, String tweetDesc);
    void deleteTweet(long tweetId, long userId);

}
