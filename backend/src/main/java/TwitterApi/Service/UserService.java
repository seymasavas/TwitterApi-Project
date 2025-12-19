package TwitterApi.Service;

import TwitterApi.dto.UserResponse;
import TwitterApi.dto.request.UserLoginRequest;
import TwitterApi.dto.request.UserRegisterRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    UserResponse login(UserLoginRequest request);
}
