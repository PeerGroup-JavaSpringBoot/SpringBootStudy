package dev.yoon.basic_community.service;


import dev.yoon.basic_community.domain.Area;
import dev.yoon.basic_community.domain.user.User;
import dev.yoon.basic_community.dto.UserDto;
import dev.yoon.basic_community.exception.NameDuplicationException;
import dev.yoon.basic_community.exception.UserNotFoundException;
import dev.yoon.basic_community.repository.AreaRepository;
import dev.yoon.basic_community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserDto.Res createUser(UserDto.SignUpReq dto) {

        isExistedName(dto.getName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));

        Optional<Area> optionalArea = areaRepository.findById(1L);

        User user = User.builder()
                .username(dto.getName())
                .residence(optionalArea.get())
                .password(dto.getPassword())
                .isShopOwner(dto.getUserCategory())
                .build();

        this.userRepository.save(user);

        return new UserDto.Res(user);

    }

    public List<UserDto.Res> readUserAll() {

        List<User> users = userRepository.findAll();

        List<UserDto.Res> res = users.stream().parallel()
                .map(user -> new UserDto.Res(user))
                .collect(Collectors.toList());

        return res;
    }

    public UserDto.Res readUserOne(Long userId) {

        return new UserDto.Res(findById(userId));
    }

    public boolean updateUser(Long userId, UserDto.Req userDto) {

        User user = findById(userId);
        user.updateUser(userDto);

        return true;
    }

    public boolean deleteUser(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
        return true;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> account = userRepository.findById(id);
        account.orElseThrow(() -> new UserNotFoundException(id));
        return account.get();
    }

    @Transactional(readOnly = true)
    public void isExistedName(String username) {

        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent())
            throw new NameDuplicationException(username);

    }

}
