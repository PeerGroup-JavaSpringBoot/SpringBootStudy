package dev.aquashdw.community.auth;

import dev.aquashdw.community.entity.AreaEntity;
import dev.aquashdw.community.entity.UserEntity;
import dev.aquashdw.community.auth.model.AutoLockUserDetails;
import dev.aquashdw.community.repository.AreaRepository;
import dev.aquashdw.community.repository.UserRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Primary
@Service
public class CommunityUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CommunityUserDetailsService.class);
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final PasswordEncoder passwordEncoder;

    public CommunityUserDetailsService(
            @Autowired UserRepository userRepository,
            @Autowired AreaRepository areaRepository,
            @Autowired PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        logger.info("username: {}, last logged in at: {}", userEntity.getUsername(), userEntity.getLastLogin());
//        return new User(
//                userEntity.getUsername(),
//                userEntity.getPassword(),
//                Collections.emptyList()
//        );
        return new AutoLockUserDetails(userEntity);
    }

    public void createUser(String username, String password, Boolean isShopOwner) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setShopOwner(isShopOwner);

        AreaEntity randomArea = this.randomArea();
        newUser.setResidence(randomArea);

        this.userRepository.save(newUser);
    }

    private AreaEntity randomArea(){
        List<AreaEntity> areaEntityList = new ArrayList<>();
        Iterable<AreaEntity> areaIterable = this.areaRepository.findAll();
        areaIterable.forEach(areaEntityList::add);
        Random random = new Random();
        return areaEntityList.get(
                random.nextInt(areaEntityList.size()));
    }
}
