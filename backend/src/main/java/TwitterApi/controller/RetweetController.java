package TwitterApi.controller;

import TwitterApi.Service.RetweetService;
import TwitterApi.dto.RetweetResponse;
import TwitterApi.dto.request.RetweetRequest;
import TwitterApi.entity.Retweet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private final RetweetService retweetService;

    @PostMapping
    public RetweetResponse newRetweet(@RequestBody @Valid RetweetRequest request){
        Retweet retweet=retweetService.newRetweet(request.tweetId(),request.userId());

        return new RetweetResponse(retweet.getId(),retweet.getUser().getId(),retweet.getTweet().getId(),retweet.getRetweetTime());
    }

    @DeleteMapping("/{id}")
    public void deleteRetweet(@PathVariable ("id") long retweetId, @RequestParam long userId){
        retweetService.deleteRetweet(retweetId,userId);
    }
}
