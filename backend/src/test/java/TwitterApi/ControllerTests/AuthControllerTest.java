package TwitterApi.ControllerTests;


import TwitterApi.Service.UserService;
import TwitterApi.controller.AuthController;
import TwitterApi.dto.UserResponse;
import TwitterApi.dto.request.UserLoginRequest;
import TwitterApi.dto.request.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private UserService userService;

        @Autowired
        private ObjectMapper objectMapper;
        private UserResponse mockUserResponse;

    @BeforeEach
    void setUp() {
        mockUserResponse = new UserResponse(1L, "Seyma", "test@test.com", LocalDateTime.now());
    }
    @DisplayName("Kayıt olma")
    @Test
    void shouldRegisterSuccessfully() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("Seyma Savas", "test@test.com", "123456");

        when(userService.register(any(UserRegisterRequest.class))).thenReturn(mockUserResponse);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @DisplayName("Login olma")
    @Test
    void shouldLoginSuccessfully() throws Exception {

        UserLoginRequest request = new UserLoginRequest("test@test.com", "123456");

        when(userService.login(any(UserLoginRequest.class))).thenReturn(mockUserResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}
