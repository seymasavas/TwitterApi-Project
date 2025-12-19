package TwitterApi.controller;

import TwitterApi.Service.UserService;
import TwitterApi.dto.UserResponse;
import TwitterApi.dto.request.UserLoginRequest;
import TwitterApi.dto.request.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3200")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid UserRegisterRequest request) {
        return userService.register(request);
    }
    @PostMapping("/login")
    public UserResponse login(@RequestBody @Valid UserLoginRequest request) {
        return userService.login(request);
    }
}