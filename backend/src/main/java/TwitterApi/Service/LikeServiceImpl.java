package TwitterApi.Service;

import TwitterApi.entity.Like;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import TwitterApi.exception.TwitterException;
import TwitterApi.repository.LikeRepository;
import TwitterApi.repository.TweetRepository;
import TwitterApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final LikeRepository likeRepository;

    @Override
    public Like likeTweet(long tweetId, long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new TwitterException("user bulunamadı",HttpStatus.NOT_FOUND));
        Tweet tweet=tweetRepository.findById(tweetId).orElseThrow(()->new TwitterException("tweet bulunamadı.",HttpStatus.NOT_FOUND));

        Like likes=new Like();

        if(likeRepository.findByUserAndTweet(user,tweet).isPresent()){
            throw new TwitterException("Bu tweet like'lanmış", HttpStatus.NOT_FOUND);
        }else{
            likes.setTweet(tweet);
            likes.setUser(user);
            likes.setCreatedAt(LocalDateTime.now());
            likeRepository.save(likes);
        }
        return likes;
    }

    @Override
    public void dislikeTweet(long tweetId, long userId) {
        Tweet tweet=tweetRepository.findById(tweetId).orElseThrow(()->new TwitterException("tweet bulunamadı.",HttpStatus.NOT_FOUND));
        User user=userRepository.findById(userId).orElseThrow(()->new TwitterException("user bulunamadı.",HttpStatus.NOT_FOUND));
        Like like=likeRepository.findByUserAndTweet(user,tweet).orElseThrow(()-> new TwitterException("Bu tweet'i bu user like'lamamıştır.",HttpStatus.NOT_FOUND));

        likeRepository.delete(like);

    }
}


