# Chapter 4: Spring Security

애플리케이션을 만들기 위해서는 보통 인증/인가 등의 보안이 필요하다. 스프링 시큐리티는 스프링 기반의 애플리케이션을 위한 보안 솔루션을 제공

애플리케이션의 보안에서 중요한 두 가지 영역은 `인증`과 `인가`

- 웹이서 인증이란 해당 리소스에 대해서 작업을 수행할 수 있는 주체인지 확인하는 것
  - 예를 들어 어떤 커뮤니티에서 게시판의 글을 보는 것은 로그인을 하지 않아도 되지만, 댓글을 작성하려면 로그인을 해야한다. 댓글을 달기 위해서는 로그인이라는 인증 절차가 필요
- 인가는 인증 과정 이후에 일어난다.
  - 커뮤니티를 관리하는 관리자 페이지에 접근하는 URL을 입력했을 때 해당 URL은 커뮤니티의 관리자만 접근할 수 있어야 한다. 
  - 이때 접근하는 사용자가 해당 URL에 대해서 인가된 회원인지를 검사하는 것, 인가된 유저라면 해당 URL에 대한 권한이 있기 때문에 접근이 가능

> 인증(Authentication)과 인가(Authorization) 
> 
> 인증: 유저가 누구인지 확인하는 절차, 회원과입과 로그인하는 것
> 
> 인가: 유저에 대한 권한을 허락하는 것 

## 스프링 시큐리티 설정 추가하기

```java
dependencies {
        ...
    implementation 'org.springframework.boot:spring-boot-starter-security'
    testImplementation 'org.springframework.security:spring-security-test'
        ...
}
```

- 시큐리티 설정을 추가함으로써 모든 요청에 인증을 요구한다.
- 인증이 필요 없는 경우: 상품 상세 페이지 조회
- 인증이 필요한 경우: 상품 주문
- 관리자 권한이 필요한 경우: 상품 등록

```java
@Configuration
@EnableWebSecurity // 1)
public class SecurityConfig extends WebSecurityConfigurerAdapter { 


    @Override
    protected void configure(HttpSecurity http) throws Exception { // 2)
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() { // 3)
        return new BCryptPasswordEncoder();
    }
}
```

1. WebSecurityConfigureAdapter를 상속받는 클래스에 `@EnableWebSecurity` 어노테이션을 선언하면 SpringSecurityFilterChain이 자동으로 포함, WebSecurityConfigurerAdapter를 상속받아서 메소드 오버라이딩을 통해 보안 설정을 커스터마이징할 수 있다.
2. http 요청에 대한 보안을 설정한다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
3. 비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출된다. 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장한다.

---

> CSRF
> 
> CSRF(Cross Site Request Forgery)란 사이트간 위조 요청으로 자신의 의지와 상관없이 해커가 의도한 대로 수정, 등록, 삭제 등의 행위를 웹사이트 요청하게 하는 공격

- 스프링 시큐리티를 사용할 경우 기본적으로 CSRF를 방어하기 위해 모든 POST 방식의 데이터 전송에는 CSRF 토큰 값이 있어야 한다. CSRF 토큰은 실제 서버에서 허용한 요청이 맞는지 확인하기 위한 토큰, 사용자의 세션에 임의의 값을 저장하여 요청마다 그 값을 포함하여 전송하면 서버에서 세션에 저장된 값과 요청이 온 값이 일치하는지 확인하여 CSRF를 방어한다.

## 로그인/로그아웃 구현하기

### UserDetailsService

UserDetailsService 인터페이스는 데이터베이스에서 회원 정보를 가져오는 역할을 담당, loadUserByUserName() 메소드가 존재하여 사용자의 정보와 권한을 갖는 UserDetails 인터페이스를 반환

### UserDetail

스프링 시큐리티에서 회원의 정보를 담기 위해서 사용하는 인터페이스, 해당 인텊페이스를 직접 구현하거나 스프링 시큐리티에서 제공하는 User 클래스를 사용한다.

User 클래스는 UserDetails 인터페이스를 구현하고 있는 클래스 

```java
package dev.yoon.shop.global.config.security;

import dev.yoon.shop.domain.member.application.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  ...
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") 
                .defaultSuccessUrl("/") 
                .usernameParameter("email") 
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 1)
                .logoutSuccessUrl("/");
    }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(memberService) // 2)
            .passwordEncoder(passwordEncoder()); // 3)
  }
  ...
}

```
1. 로그아웃 URL을 설정
2. Spring Security에서 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManagerBuilder가 AuthenticationManager를 생성, userDetailService를 구현하고 있는 객체로 memberService를 지정
3. 비밀번호 암호화를 위해 passwordEncoder를 지정

### 로그인 테스트 코드

```java
@SpringBootTest
@AutoConfigureMockMvc // 1)
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc; // 2)

    public Member createMember(String email, String password) {

        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setEmail(email);
        memberRegisterDto.setName("홍길동");
        memberRegisterDto.setAddress("서울시 송파구 가락동");
        memberRegisterDto.setPassword(password);

        return memberRegisterDto.toEntity();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login") // 3) 
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); // 4)
    }

}
```

1. MockMvc 테스트를 위해 `@AutoconfigureMockMvc` 어노테이션을 선언
2. MockMvc 클래스를 이용해 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체, MockMvc 객체를 이용하면 웹 브라우저에서 요청을 하는 것처럼 테스트할 수 있다.
3. 회원가입 메소드를 실행 후 가입된 회원 정보로 로그인이 되는지 테스트를 진행, userParameter()를 이용하여 이메일을 아이디로 세팅하고 로그인 URL에 요청

## 페이지 권한 설정하기

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  ...
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      ...

        http.authorizeRequests() // 1)
                .mvcMatchers("/", "/members/**",
                        "/item/**", "/images/**").permitAll() // 2)
                .mvcMatchers("/admin/**").hasRole("ADMIN") // 3)
                .anyRequest().authenticated(); // 4)

        http.exceptionHandling()
                .authenticationEntryPoint(
                        new CustomAuthenticationEntryPoint() // 5)
                );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); // 6)
    }
  ...
}
```

1. 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미
2. permitAll()을 통해 모든 사용자가 인증(로그인)없이 해당 경로에 접근할 수 있도록 설정한다.
3. /admin으로 시작하는 경로는 해당 계정이 ADMIN Role일 경우에만 접근 가능하도록 설정
4. 2,3 이외의 경로에 대해서는 모두 인증을 요구하도록 설정한다. 
5. 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록
6. static 디렉토리의 하위 파일은 인증을 무시하도록 설정

### 상품 등록 페이지 권한 테스트

```java
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN") // 1)
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) // 2)
                .andDo(print()) // 3)
                .andExpect(status().isOk()); // 4)
    }

  ...
}
```

1. 현재 회원의 이름이 admin 이고, role이 ADMIN인 유저가 로그인된 상태로 테스트를 할 수 있도록 해주는 어노테이션
2. 상품 등록 페이지에 get요청을 보낸다.
3. 요청과 응답 메시지를 확인할 수 있도록 콘솔창에 출력
4. 응답 상태 코드가 정상인지 확인

### 상품 등록 페이지 일반 회원 접근 테스트

```java
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class ItemControllerTest {

  @Autowired
  MockMvc mockMvc;
  ...
  @Test
  @DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
  @WithMockUser(username = "user", roles = "USER") // 1)
  public void itemFormNotAdminTest() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
            .andDo(print())
            .andExpect(status().isForbidden()); // 2)
  }
```
1. 현재 인증된 사용자의 Role을 USER로 세팅
2. 상품 등록 페이지 진입 요청 시 Forbidden 예외가 발생하면 테스트가 성공적으로 통과

---
# Mock

사전적 의미로 '테스트를 위해 만든 모형'을 의미하고, 테스트를 위해 실제 객체와 비슷한 모의 객체를 만드는 것을 **모킹(Mocking)**, 모킹한 객체를 메모리에서 얻어내는 과정을 **목업(Mock-up)**이라 한다.

## MockMvc?

`MockMvc`는 웹 어플리케이션을 **서버에 배포하지 않고도 Spring MVC의 동작을 재현하여 테스트 할 수 있는 클래스**를 의미한다.
이는 실제 객체와 비슷하지만 **테스트에 필요한 기능만 가지고 있는 가짜 객체**를 만들어 사용하는 방법이다.

### MockMvc 설정

#### ContextHierychy

테스트용 DI 컨테이너 만들 대 Bean 파일을 지정한다. 

```java
@ContextHierarchy({
	@ContextConfiguration(classes = AppConfig.class),
    @ContextConfiguration(classes = WebMvcConfig.class)
})
```

#### WebAppConfiguration

Controller 및 web 환경에 사용되는 빈을 자동으로 생성하여 등록

### MockMvc 실행 

perform() 메소드를 이용하여 설정한 MockMvc를 실행

```java
 @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void 상품_등록_페이지_권한_테스트() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }
```


### MockMvc 요청 설정 메소드

- param / params : 쿼리 스트링 설정
- cookie : 쿠키 설정
- requestAttr : 요청 스코프 객체 설정
- sessionAttr : 세션 스코프 객체 설정
- content : 요청 본문 설정
- header / headers : 요청 헤더 설정
- contentType : 본문 타입 설정

```java
@Test
public void testController() throws Exception{
	
    mockMvc.perforem(get("test"))
    	.param("query", "부대찌개")
        .cookie("쿠키 값")
        .header("헤더 값:)
        .contentType(MediaType.APPLICATION.JSON)
        .content("json으로");
}
```

### MockMvc 검증 메소드

- status : 상태 코드 검증
- header : 응답 header 검증
- content : 응답 본문 검증
- cookie : 쿠키 상태 검증
- view : 컨트롤러가 반환한 뷰 이름 검증
- redirectedUrl(Pattern) : 리다이렉트 대상의 경로 검증
- model : 스프링 MVC 모델 상태 검증
- request : 세션 스코프, 비동기 처리, 요청 스코프 상태 검증
- forwardedUrl : 이동대상의 경로 검증

```java
@Test
public void testController() throws Exception{
	mockMvc.perform(get("test"))
    	.param("query", "부대찌개")
        .cooke("쿠키 값")
        .header("헤더 값:)
        .contentType(MediaType.APPLICATION.JSON)
        .content("json으로")
        .andExpect(status().isOk()) // 여기부터 검증
        .andExpect(content().string("expect json값"))
        .andExpect(view().string("뷰이름"));
```

### MockMvc 기타 메소드

- andDo() : print, log를 사용할 수 있는 메소드
- print() : 실행결과를 지정해준 대상으로 출력, default = System.out
- log() : 실행결과를 디버깅 레벨로 출력, 레벨은 org.springframework.test.web.servlet.result

```java
@Test
public void testController() throws Exception{
	
    mockMvc.perforem(get("test"))
    	.param("query", "부대찌개")
        .cookie("쿠키 값")
        .header("헤더 값:)
        .contentType(MediaType.APPLICATION.JSON)
        .content("json으로")
        
        .andExpect(status().isOk()) 
        .andExpect(content().string("expect json값"))
        .andExpect(view().string("뷰이름"))
        
        .andDo(print()) //여기부터 기타 메소드
        .andDo(log());
```