package TwitterApi.repository;

import TwitterApi.entity.Like;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByUserAndTweet(User user, Tweet tweet);

}


