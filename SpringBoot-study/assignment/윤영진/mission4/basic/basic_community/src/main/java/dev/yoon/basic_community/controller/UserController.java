package dev.yoon.basic_community.controller;


import dev.yoon.basic_community.domain.user.UserCategory;
import dev.yoon.basic_community.dto.UserDto;
import dev.yoon.basic_community.exception.PasswordNotEqualsPasswordCheckException;
import dev.yoon.basic_community.service.CommunityUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final CommunityUserDetailsService communityUserDetailsService;

    @GetMapping("login")
    public String login() {
        return "login-form";
    }

    @GetMapping("signup")
    public String signUp() {
        return "signup-form";
    }

    @PostMapping("signup")
    public String signUpPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password_check") String passwordCheck,
            @RequestParam(value = "is_shop_owner", required = false) boolean isShopOwner
    ) {
        if (!password.equals(passwordCheck))
            throw new PasswordNotEqualsPasswordCheckException();

        UserDto.SignUpReq dto = UserDto.SignUpReq.builder()
                .name(username)
                .password(password)
                .userCategory(isShopOwner ? UserCategory.OWNER : UserCategory.GENERAL)
                .build();

        communityUserDetailsService.createUser(dto);
        return "redirect:/home";
    }


//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<UserDto.Res> createUser(
//            @RequestBody @Valid UserDto.SignUpReq userDto) {
//        return ResponseEntity.ok(this.communityUserDetailsService.createUser(userDto));
//    }
//
//    @GetMapping()
//    public ResponseEntity<Result<List<UserDto.Res>>> readUserAll(
//    ) {
//        List<UserDto.Res> userDtos = this.communityUserDetailsService.readUserAll();
//        Result result = new Result(userDtos.size(),userDtos);
//
//        return ResponseEntity.ok(result);
//    }
//    @GetMapping("{userId}")
//    public ResponseEntity<UserDto.Res> readUserOne(
//            @PathVariable("userId") Long userId) {
//
//        return ResponseEntity.ok(this.communityUserDetailsService.readUserOne(userId));
//    }
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("{userId}")
//    public ResponseEntity<?> updateUser(
//            @PathVariable("userId") Long userId,
//            @RequestBody UserDto.Req userDto) {
//        communityUserDetailsService.updateUser(userId, userDto);
//        return ResponseEntity.noContent().build();
//    }
//
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @DeleteMapping("{userId}")
//    public ResponseEntity<?> deleteUser(
//            @PathVariable("userId") Long userId) {
//        this.communityUserDetailsService.deleteUser(userId);
//
//        return ResponseEntity.noContent().build();
//
//    }
}
