package dev.aquashdw.community.auth;

import dev.aquashdw.community.entity.UserEntity;
import dev.aquashdw.community.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        UserEntity userEntity = this.userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("inconsistent: user not found after successful login")
        );

        userEntity.setLastLogin(Instant.now());
        this.userRepository.save(userEntity);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
