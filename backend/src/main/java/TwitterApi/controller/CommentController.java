package TwitterApi.controller;

import TwitterApi.Service.CommentService;
import TwitterApi.dto.CommentResponse;
import TwitterApi.dto.request.CommentCreateRequest;
import TwitterApi.dto.request.CommentUpdateRequest;
import TwitterApi.entity.Comment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController{
    private final CommentService commentService;

    @PostMapping
    public CommentResponse newComment(@RequestBody @Valid CommentCreateRequest request){
        Comment newComment=commentService.newComment(request.tweetId(), request.userId(), request.commentDesc());
       return new CommentResponse(newComment.getId(), newComment.getCommentDesc(),newComment.getUser().getId(),
               newComment.getTweet().getId(),newComment.getCommentTime());

    }
    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable("id") long commentId,
                                         @RequestBody @Valid CommentUpdateRequest request) {
        Comment updatedComment=commentService.updateComment(request.userId(),commentId,request.commentDesc());
        return new CommentResponse(updatedComment.getId(),updatedComment.getCommentDesc(),
                updatedComment.getUser().getId(), updatedComment.getTweet().getId(),updatedComment.getCommentTime());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") long commentId,
                              @RequestParam long userId){
        commentService.deleteComment(userId,commentId);
    }
}
