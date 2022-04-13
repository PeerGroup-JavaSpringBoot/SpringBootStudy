package jsbdy.mission3.controller;

import jsbdy.mission3.entity.BoardEntity;
import jsbdy.mission3.entity.UserEntity;
import jsbdy.mission3.model.BoardDto;
import jsbdy.mission3.model.UserDto;
import jsbdy.mission3.repository.BoardRepository;
import jsbdy.mission3.repository.UserRepository;
import jsbdy.mission3.service.BoardService;
import jsbdy.mission3.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(
            UserRepository userRepository,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @RequestBody UserDto userDto
    ) {
        this.userService.createUser(userDto);
    }

    @GetMapping("{id}")
    public UserEntity readUser(
            @PathVariable("id") int id
    ) {
        Optional<UserEntity> userentity = this.userRepository.findById((long) id);
        if (userentity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userentity.get();

    }

    @GetMapping()
    public Iterator<UserEntity> readUserAll() {
        return this.userRepository.findAll().iterator();
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(
            @PathVariable("id") int id,
            @RequestBody UserDto userDto
    ) {
        this.userService.updateUser(id, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(
            @PathVariable("id") int id
    ) {
        Optional<UserEntity> targetentity = this.userRepository.findById((long) id);
        if (targetentity.isEmpty()) {
            logger.info("user is deleted already");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
        this.userRepository.delete(targetentity.get());
    }

}

