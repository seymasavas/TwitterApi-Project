package TwitterApi.Service;

import TwitterApi.entity.Comment;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import TwitterApi.exception.TwitterException;
import TwitterApi.repository.CommentRepository;
import TwitterApi.repository.TweetRepository;
import TwitterApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public Comment newComment(long tweetId, long userId, String commentDesc) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new TwitterException("Tweet bulunamadı",HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new TwitterException("User bulunamadı.", HttpStatus.NOT_FOUND));
        Comment comment=new Comment();
        comment.setCommentDesc(commentDesc);
        comment.setCommentTime(LocalDateTime.now());
        comment.setUser(user);
        comment.setTweet(tweet);

        commentRepository.save(comment);
        return comment;

    }

    @Override
    public Comment updateComment(long userId, long commentId, String commentDesc) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new TwitterException("Comment bulunamadı.",HttpStatus.NOT_FOUND));

       if(comment.getUser().getId()==userId){
           comment.setCommentDesc(commentDesc);
           commentRepository.save(comment);
       }else{
           throw new TwitterException("Buna yetkin yok.",HttpStatus.FORBIDDEN);
       }

        return comment;
    }

    @Override
    public void deleteComment(long userId, long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new TwitterException("Comment bulunamadı.",HttpStatus.NOT_FOUND));

        if(comment.getUser().getId()==userId ||comment.getTweet().getUser().getId()==userId){
            commentRepository.delete(comment);
        }else{
            throw new TwitterException("Buna yetkin yok.",HttpStatus.FORBIDDEN);
        }
    }
}


