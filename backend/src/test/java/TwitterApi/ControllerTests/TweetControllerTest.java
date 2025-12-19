package TwitterApi.ControllerTests;

import TwitterApi.Service.TweetService;
import TwitterApi.controller.TweetController;
import TwitterApi.dto.request.TweetRequest;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
@AutoConfigureMockMvc
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TweetService tweetService;

    @Autowired
    private ObjectMapper objectMapper;

    private Tweet createTweet() {
        User user = new User();
        user.setId(1L);

        Tweet tweet = new Tweet();
        tweet.setId(10L);
        tweet.setTweetDesc("ilk tweet");
        tweet.setTweetTime(LocalDateTime.now());
        tweet.setUser(user);

        return tweet;
    }
    @DisplayName("UserId ile tweet bulma")
    @Test
    void shouldGetTweetsByUserId() throws Exception {
        when(tweetService.getTweetsByUserId(1L))
                .thenReturn(List.of(createTweet()));

        mockMvc.perform(get("/tweet/findByUserId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].tweetDesc").value("ilk tweet"));

    }
    @DisplayName("id ile tweet bulma")
    @Test
    void shouldGetTweetById() throws Exception {
        when(tweetService.getTweetById(10L))
                .thenReturn(createTweet());

        mockMvc.perform(get("/tweet/findById/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tweetId").value(10L))
                .andExpect(jsonPath("$.tweetDesc").value("ilk tweet"));
    }

    @DisplayName("Tweet oluşturma")
    @Test
    void shouldCreateTweet() throws Exception {

        TweetRequest request = new TweetRequest(1L, "yeni tweet");

        when(tweetService.newTweet(anyLong(), anyString()))
                .thenReturn(createTweet());

        mockMvc.perform(post("/tweet")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tweetDesc").value("ilk tweet"));
    }

    @DisplayName("Tweet update")
    @Test
    void shouldUpdateTweet() throws Exception {
        when(tweetService.updateTweet(eq(10L), eq(1L), eq("güncel tweet")))
                .thenReturn(createTweet());

        mockMvc.perform(put("/tweet/10")
                        .contentType("application/json")
                        .content("""
                                  {
                                  "userId": 1,
                                  "tweetDesc": "güncel tweet"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @DisplayName("Tweet silme")
    @Test
    void shouldDeleteTweet() throws Exception {
        doNothing().when(tweetService).deleteTweet(10L, 1L);

        mockMvc.perform(delete("/tweet/10")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }
}
