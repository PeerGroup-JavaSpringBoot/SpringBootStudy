package dev.yoon.basic_board.service;

import dev.yoon.basic_board.domain.Area;
import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.domain.User;
import dev.yoon.basic_board.dto.UserDto;
import dev.yoon.basic_board.repository.AreaRepository;
import dev.yoon.basic_board.repository.PostRepository;
import dev.yoon.basic_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final PostRepository postRepository;

    public UserDto createUser(UserDto userDto) {
        User user = new User(userDto);

        Area area = new Area();
        area.setAddress(userDto.getAddress());
        area.setLocation(userDto.getLocation());
        areaRepository.save(area);
        user.addArea(area);

        userRepository.save(user);
        return userDto;

    }

    public List<UserDto> readUserAll() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserDto(user,user.getPostList()))
                .collect(Collectors.toList());
    }

    public UserDto readUserOne(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        List<Post> postList = postRepository.findPostAllbyUserId(userId);
        if (optionalUser.isEmpty())
            return null;

        UserDto dto = new UserDto(optionalUser.get(), postList);
        return dto;
    }

    public boolean updateUser(Long userId, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return false;

        User user = optionalUser.get();
        user.update(userDto);
        return true;

    }

    public boolean deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return false;
        userRepository.deleteById(userId);
        return true;
    }
}
