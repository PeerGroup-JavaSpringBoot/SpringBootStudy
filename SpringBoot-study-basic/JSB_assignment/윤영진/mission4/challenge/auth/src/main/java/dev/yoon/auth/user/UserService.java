package dev.yoon.auth.user;

import dev.yoon.auth.user.entity.UserEntity;
import dev.yoon.auth.user.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Primary
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(
            @Autowired
            PasswordEncoder passwordEncoder,
            @Autowired
            UserRepository userRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        userRepository.save(new UserEntity("user1", passwordEncoder.encode("pass1")));
        userRepository.save(new UserEntity("user2", passwordEncoder.encode("pass2")));
        userRepository.save(new UserEntity("user3", passwordEncoder.encode("pass3")));

    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if(!userRepository.existsByUsername(username)){
            logger.warn(username);
            throw new UsernameNotFoundException(String.format("username %s not found", username));
        }

        UserEntity userEntity = userRepository.findByUsername(username);
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                new ArrayList<>()
        );
    }
}
