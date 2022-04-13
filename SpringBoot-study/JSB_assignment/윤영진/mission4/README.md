# Spring_Boot_Mission
[LikeLion]The Origin: Java Spring Boot 과제

### 4차 미션 스크린샷
## Basic

### `CommunityUserDetailsService` 클래스를 정의하고, `UserDetailsService` 의 구현체로 선언합시다.
```
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

	...
}

```

#### `UserRepository` 를 통해 받아온 `UserEntity` 를 `UserDetails` 의 형태로 반환할 수 있어야 합니다.

```

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "community_user")
public class User extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserCategory isShopOwner;

    @ManyToOne(
            targetEntity = Area.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "area_id")
    private Area residence;

    @Builder
    public User(String username, String password, Area residence, UserCategory isShopOwner) {
        this.username = username;
        this.residence = residence;
        this.password = password;
        this.isShopOwner = isShopOwner;
    }

    public User(UserDto.SignUpReq dto) {
        this.username = dto.getName();
        this.password = dto.getPassword();
        this.isShopOwner = dto.getUserCategory();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateUser(UserDto.Req userDto) {

        this.username = userDto.getName();
        this.isShopOwner = userDto.getUserCategory();

    }
}
```

#### `UserController` 라고 `@Controller` Bean을 만들고, 강의와 유사하게 로그인, 회원가입 등의 기능을 추가합시다.

```
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

	...

}

```

### 실행 화면

#### 로그인

- 로그인

![image](https://user-images.githubusercontent.com/83503188/159317360-2fb0f36f-d850-458e-b667-62ba9d9dac36.png)

![image](https://user-images.githubusercontent.com/83503188/159317016-dd238279-40de-4122-9f08-9dbec7ed675f.png)


#### 회원가입

- 회원가입

![image](https://user-images.githubusercontent.com/83503188/159316255-a13684c5-3797-43ea-bdcf-d9fa44f824fc.png)

- 중복 username

![image](https://user-images.githubusercontent.com/83503188/159315022-8464c4fc-c976-422a-bd40-6fbe6af3882e.png)


## Challenge

#### Community Project에 새로운 Filter를 정의합니다.

```
@Slf4j
@Component
public class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("likelion_login_cookie")) {
                    log.info("Cookie Found");
                    String queryString = httpServletRequest.getQueryString();
                    if(queryString!=null) {
                        String value = queryString.split("likelion_login_cookie=")[1];
                        Cookie cookie = new Cookie("likelion_login_cookie", value);
                        cookie.setPath("/");
                        httpServletResponse.addCookie(cookie);
                        setAuthentication();
                    }
                    break;
                }
                if (i == cookies.length - 1) {
                    log.info("Cookie Not Found");
                }
            }
        }

        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void setAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return "Details";
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "dummy";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return "dummy";
            }
        });
    }
}

```

#### `Community Project` 의 임시 홈페이지를 만들고, 로그인 버튼을 추가하여 클릭시 SSO 서버로 Redirect가 진행되도록 만듭니다.

- 구체적인 경로는 /request-login 으로 하고, Query Parameter 로 request_from 에 마지막 요청 위치가 포함되도록 합니다.
```
@PostMapping("request-login")
    public void loginPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        response.sendRedirect("http://localhost:10000/request-login?request_from=" + request.getRemoteAddr());
    }
```
![image](https://user-images.githubusercontent.com/83503188/159322568-8aa2e6b6-c6df-4987-a0a6-016e3b35efc5.png)

- 로그인 성공 이후, Cookie에 likelion_login_cookie 를 임의의 값으로 추가합시다.
- 로그인 성공 후  /request-login 로, 전달받은 데이터를 잃어버리지 않고 돌아가도록 합니다.

```
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        System.out.println("CustomSuccessHandler !!!");

        Cookie cookie = new Cookie("likelion_login_cookie", "test_value");
        cookie.setPath("/");
        response.addCookie(cookie);

//        RegisteredClient registeredClient = registeredClientService.findByClientId("likelion-client");
//        Set<String> redirectUris = registeredClient.getRedirectUris();
//
//        Iterator<String> it = redirectUris.iterator();
        response.sendRedirect("http://localhost:10000/request-login");

    }
}
```

![image](https://user-images.githubusercontent.com/83503188/159324607-fe09a75e-0fcb-4a63-aa4e-a9f30945031f.png)

- 로그인 진행후 /request-login 로 돌아와, 본래 요청을 보냈던 Community Project 로 Redirect 하도록 구성해 봅시다. 이때, Cookie에 추가한 likelion_login_cookie 역시 Query Parameter 에 추가합니다.

```
@GetMapping("/request-login")
    public String login(
            @RequestParam(value = "request_from", required = false) String request_from,
            HttpServletResponse response,
            HttpServletRequest request) throws IOException {
        System.out.println("login");
        System.out.println(request_from);

        if (request_from == null) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("likelion_login_cookie")) {
                    response.sendRedirect("http://localhost:9080/user/login?likelion_login_cookie=" + cookies[i].getValue());
                    break;
                }
            }
            return null;
        }
        return "user/loginForm";
    }
```

#### 앞서 Communtiy Project 에서 구성하였던 Filter에서, (HttpServletRequest) request 의 getQueryString() 에서 likelion_login_cookie 를 찾아내, Cookie에 저장하도록 합시다.
```
if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("likelion_login_cookie")) {
                    log.info("Cookie Found");
                    String queryString = httpServletRequest.getQueryString();
                    if(queryString!=null) {
                        String value = queryString.split("likelion_login_cookie=")[1];
                        Cookie cookie = new Cookie("likelion_login_cookie", value);
                        cookie.setPath("/");
                        httpServletResponse.addCookie(cookie);
                        setAuthentication();
                    }
                    break;
                }
                if (i == cookies.length - 1) {
                    log.info("Cookie Not Found");
                }
            }
        }
```

#### Filter 내부에서 SecurityContextHolder.getContext() 가 정상적으로 작동하는지 확인합니다.
```
private void setAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return (Principal) () -> "dummy";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }

            @Override
            public String getName() {
                return "dummy";
            }
        });
    }
```

- 로그인 성공

![image](https://user-images.githubusercontent.com/83503188/159327530-eed4abd2-a43d-4c91-8939-50b453c9260b.png)


