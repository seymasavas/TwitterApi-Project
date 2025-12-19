package TwitterApi.Service;

import TwitterApi.entity.Comment;

public interface CommentService {
    Comment newComment(long tweetId, long userId, String commentDesc);
    Comment updateComment(long userId, long commentId, String commentDesc);
    void deleteComment(long userId, long commentId);
}
