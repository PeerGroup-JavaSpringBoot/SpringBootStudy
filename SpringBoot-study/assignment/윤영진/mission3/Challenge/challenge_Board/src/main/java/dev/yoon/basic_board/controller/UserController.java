package dev.yoon.basic_board.controller;

import dev.yoon.basic_board.dto.Result;
import dev.yoon.basic_board.dto.UserDto;
import dev.yoon.basic_board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserDto userDto) {
        UserDto dto = this.userService.createUser(userDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Result<List<UserDto>>> readUserAll(
    ) {
        List<UserDto> userDtos = this.userService.readUserAll();
        Result result = new Result(userDtos.size(),userDtos);

        if (userDtos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> readUserOne(
            @PathVariable("userId") Long userId) {
        UserDto userDto = this.userService.readUserOne(userId);
        if (userDto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserDto userDto) {

        if (!userService.updateUser(userId, userDto))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("userId") Long userId) {

        if (!this.userService.deleteUser(userId))
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }

}
