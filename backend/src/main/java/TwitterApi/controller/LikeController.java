package TwitterApi.controller;

import TwitterApi.Service.LikeService;
import TwitterApi.dto.LikeResponse;
import TwitterApi.entity.Like;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import TwitterApi.dto.request.LikeRequest;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class LikeController {
    private final LikeService likeService;


    @PostMapping("/like")
    public LikeResponse likeTweet(@RequestBody @Valid LikeRequest request){
        Like likedTweet=likeService.likeTweet(request.tweetId(),request.userId());

       return new LikeResponse(likedTweet.getId(), likedTweet.getTweet().getId(),likedTweet.getUser().getId(),likedTweet.getCreatedAt());

    }
    @DeleteMapping("/dislike")
    public void dislikeTweet(@RequestParam long tweetId,
                             @RequestParam long userId) {
        likeService.dislikeTweet(tweetId, userId);
    }

}
