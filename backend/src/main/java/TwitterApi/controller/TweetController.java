package TwitterApi.controller;

import TwitterApi.Service.TweetService;
import TwitterApi.dto.TweetResponse;
import TwitterApi.dto.request.TweetRequest;
import TwitterApi.entity.Tweet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tweet")
@CrossOrigin(origins = "http://localhost:3200")
public class TweetController {
    private final TweetService tweetService;

    @PostMapping
    public TweetResponse newTweet(@RequestBody @Valid TweetRequest request){
        Tweet tweet=tweetService.newTweet(request.userId(),request.tweetDesc());
        return new TweetResponse(tweet.getUser().getId(),tweet.getId(),tweet.getTweetDesc(),tweet.getTweetTime());
    }

    @GetMapping("/findByUserId/{userId}")
    public List<TweetResponse> getTweetsByUserId(@PathVariable long userId) {
        List<Tweet> tweets = tweetService.getTweetsByUserId(userId);

        return tweets.stream()
                .map(tweet -> new TweetResponse(
                        tweet.getUser().getId(),
                        tweet.getId(),
                        tweet.getTweetDesc(),
                        tweet.getTweetTime())).toList();
    }

    @GetMapping("/findById/{tweetId}")
    public TweetResponse getTweetById(@PathVariable long tweetId) {
        Tweet tweet = tweetService.getTweetById(tweetId);

        return new TweetResponse(
                tweet.getUser().getId(),
                tweet.getId(),
                tweet.getTweetDesc(),
                tweet.getTweetTime()
        );
    }

    @PutMapping("/{id}")
    public TweetResponse updateTweet(@PathVariable ("id") long tweetId,
                                     @RequestBody @Valid TweetRequest request){
        Tweet updatedTweet=tweetService.updateTweet(tweetId,request.userId(),request.tweetDesc());
        return new TweetResponse(updatedTweet.getUser().getId(),tweetId,updatedTweet.getTweetDesc(),updatedTweet.getTweetTime());
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable ("id") long tweetId, @RequestParam long userId){
        tweetService.deleteTweet(tweetId,userId);
    }

}
