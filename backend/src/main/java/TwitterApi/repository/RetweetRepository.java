package TwitterApi.repository;

import TwitterApi.entity.Retweet;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet,Long> {
    Optional<Retweet> findByUserAndTweet(User user, Tweet Tweet);

}
