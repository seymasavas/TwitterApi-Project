package TwitterApi.ControllerTests;

import TwitterApi.Service.CommentService;
import TwitterApi.controller.CommentController;
import TwitterApi.dto.request.CommentCreateRequest;
import TwitterApi.entity.Comment;
import TwitterApi.entity.Tweet;
import TwitterApi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Comment createComment() {
        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setCommentDesc("ilk yorum");
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setCommentTime(LocalDateTime.now());
        return comment;
    }

    @DisplayName("Yeni Comment Oluşturma")
    @Test
    void shouldCreateComment() throws Exception {
        CommentCreateRequest request = new CommentCreateRequest(1L, 1L,  "ilk yorum");

        when(commentService.newComment(anyLong(), anyLong(), org.mockito.ArgumentMatchers.anyString()))
                .thenReturn(createComment());

        mockMvc.perform(post("/comment")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentDesc").value("ilk yorum"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.tweetId").value(1));
    }

    @DisplayName("Comment Silme")
    @Test
    void shouldDeleteComment() throws Exception {
        mockMvc.perform(delete("/comment/1")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }
}
