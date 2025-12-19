package TwitterApi.Service;


import TwitterApi.dto.UserResponse;
import TwitterApi.dto.request.UserLoginRequest;
import TwitterApi.dto.request.UserRegisterRequest;
import TwitterApi.entity.User;
import TwitterApi.exception.TwitterException;
import TwitterApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new TwitterException("Bu email zaten kayıtlı", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(request.userName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRegistrationDate(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getRegistrationDate()
        );
    }

    @Override
    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new TwitterException("Email veya şifre hatalı", HttpStatus.UNAUTHORIZED));


        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new TwitterException("Email veya şifre hatalı", HttpStatus.UNAUTHORIZED);
        }

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRegistrationDate()
        );
    }
}



