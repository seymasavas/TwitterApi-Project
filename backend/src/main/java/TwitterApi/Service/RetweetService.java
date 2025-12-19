package TwitterApi.Service;

import TwitterApi.entity.Retweet;

public interface RetweetService {
    Retweet newRetweet(long tweetId,long userId);
    void deleteRetweet(long retweetId,long userId);
}
