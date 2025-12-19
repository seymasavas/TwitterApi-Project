package TwitterApi.Service;

import TwitterApi.entity.Like;

public interface LikeService {
  Like likeTweet(long  tweetId, long userId);
  void dislikeTweet(long  tweetId, long userId);
}

