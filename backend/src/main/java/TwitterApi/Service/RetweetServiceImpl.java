package TwitterApi.Service;

import TwitterApi.entity.Retweet;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import TwitterApi.exception.TwitterException;
import TwitterApi.repository.RetweetRepository;
import TwitterApi.repository.TweetRepository;
import TwitterApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RetweetServiceImpl implements RetweetService{
    private final TweetRepository tweetRepository;
    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;

    @Override
    public Retweet newRetweet(long tweetId, long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->
                new TwitterException("Id'si: " +userId+" olan user bulunamadı", HttpStatus.NOT_FOUND));
        Tweet tweet=tweetRepository.findById(tweetId).orElseThrow(()-> new TwitterException("tweet bulunamadı", HttpStatus.NOT_FOUND));

        Retweet retweet=new Retweet();

        if(retweetRepository.findByUserAndTweet(user,tweet).isPresent()) {
            throw  new TwitterException("Bu tweet daha önce retweet edilmiş", HttpStatus.BAD_REQUEST);
        }else{
            retweet.setUser(user);
            retweet.setTweet(tweet);
            retweet.setRetweetTime(LocalDateTime.now());
            retweetRepository.save(retweet);
        }
        return retweet;

    }

    @Override
    public void deleteRetweet(long retweetId, long userId) {
        Retweet retweet=retweetRepository.findById(retweetId).orElseThrow(()->
                new TwitterException("retweet bulunamadı", HttpStatus.NOT_FOUND));

        if(retweet.getUser().getId()==userId){
            retweetRepository.delete(retweet);
        }else {
            throw new TwitterException("Buna yetkin yok.",HttpStatus.FORBIDDEN);
        }
    }
}
