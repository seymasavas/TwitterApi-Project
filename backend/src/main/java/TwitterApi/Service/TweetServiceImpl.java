package TwitterApi.Service;

import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import TwitterApi.exception.TwitterException;
import TwitterApi.repository.TweetRepository;
import TwitterApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public Tweet newTweet(long userId, String tweetDesc) {
        User user=userRepository.findById(userId).orElseThrow(()->
                new TwitterException("user bulunamadı.", HttpStatus.NOT_FOUND));
        Tweet newTweet= new Tweet();
        newTweet.setTweetDesc(tweetDesc);
        newTweet.setTweetTime(LocalDateTime.now());
        newTweet.setUser(user);
        tweetRepository.save(newTweet);
        return newTweet;
    }

    @Override
    public List<Tweet> getTweetsByUserId(long userId) {
        userRepository.findById(userId).orElseThrow(()->
                new TwitterException("user bulunamadı.", HttpStatus.NOT_FOUND));
        return tweetRepository.findTweetByUserId(userId);
    }

    @Override
    public Tweet getTweetById(long tweetId) {
        return tweetRepository.findById(tweetId).orElseThrow(()->
                new TwitterException("tweet bulunamadı.", HttpStatus.NOT_FOUND));
    }

    @Override
    public Tweet updateTweet(long tweetId, long userId, String tweetDesc) {
        Tweet tweet=tweetRepository.findById(tweetId).orElseThrow(()->
                new TwitterException("tweet bulunamadı.", HttpStatus.NOT_FOUND));

        if(tweet.getUser().getId()== userId){
            tweet.setTweetDesc(tweetDesc);
            tweetRepository.save(tweet);
        }else{
            throw new TwitterException("Buna yetkin yok.",HttpStatus.FORBIDDEN);
        }

        return tweet;
    }

    @Override
    public void deleteTweet(long tweetId, long userId) {
        Tweet tweet=tweetRepository.findById(tweetId).orElseThrow(()->
                new TwitterException("tweet bulunamadı", HttpStatus.NOT_FOUND));

        if(tweet.getUser().getId()==userId){
            tweetRepository.delete(tweet);
        }else {
            throw new TwitterException("Buna yetkin yok.",HttpStatus.FORBIDDEN);
        }

    }
}
