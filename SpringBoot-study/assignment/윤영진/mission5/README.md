# Spring_Boot_Mission
[LikeLion]The Origin: Java Spring Boot 과제

### 5차 미션 스크린샷
## Basic

### `HTML Geolocation API를 사용해 봅시다.
```javascript
<script>
    function getLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition);
        } else {
            alert("Geolocation is not supported by this browser.");
        }
    }

    async function showPosition(position) {
        await fetch(
            `/area/get-location-info?latitude=${position.coords.latitude}&longitude=${position.coords.longitude}`
        )

    }

</script>

<input type="button" value="getLocation" onclick="getLocation()"/>
```

- AreaController 에 latitude와 longitude를 인자로 받는 RequestMapping 을 하나 작성합니다.

```java
@GetMapping("get-location-info")
    public ResponseEntity<AreaDto> findLocation(
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude
    ) {

        return ResponseEntity.ok(this.areaService.findNearestArea(latitude,longitude));
    }

```
---
## 베이직 실행화면

![image](https://user-images.githubusercontent.com/83503188/161531198-21eb085f-da46-4d0a-a09d-0fa7ffee057c.png)

![image](https://user-images.githubusercontent.com/83503188/161536380-97d87555-a15c-4caf-bf34-2ec7caa07971.png)

![image](https://user-images.githubusercontent.com/83503188/161531374-56fa786d-6b47-4c47-b86e-5dd92acc5f3f.png)


## Challenge

#### Community Project에 새로운 Filter를 정의합니다.

- auth-sso 에서 Redis를 이용해 로그인 정보를 저장합시다.

```java
@Override
public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
        ) throws IOException, ServletException{

        String cookieValue=UUID.randomUUID().toString();
        /**
         * 적재
         */
        CookieProcess cookieProcess=new CookieProcess();
        cookieProcess.setId(cookieValue);

        cookieProcess.setName(authentication.getName());
        redisRepository.save(cookieProcess);
        ...

        }
```

- SsoLoginController 에 현재 로그인한 사용자의 정보를 반환하는 RequestMapping 을 추가합시다.
  - community의 SsoAuthFilter 에서, WebClient 를 활용하여 auth-sso로 요청을 보내어 확인하도록 요청합니다.
```java
@GetMapping("current-user")
    public @ResponseBody
    CookieProcess getLoginUser(
            @RequestParam("cookie-value") String cookie_value
    ) {
        log.info("cookie-value: {}",cookie_value);
        return redisService.retrieveJob(cookie_value);
    }

@Component
@RequiredArgsConstructor
@Slf4j
@Order(value = 2)
public class SsoAuthFilter implements Filter {

    private final ClientService clientService;

    private final LogoutRepository logoutRepository;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Optional<String> authToken = authTokenFromCookie(httpServletRequest.getCookies());

        /**
         * Cookie에 값이 없는 경우
         * queryparameter에서 가져옴
         */
        if (authToken.isEmpty()) {
            authToken = authTokenFromQuery(httpServletRequest, httpServletResponse);
        }

        if (authToken.isPresent()) {
            log.info("Login Token value: {}", authToken.get());
            /**
             * SSO에서 로그인 정보를 가져옴
             */
            CookieProcess loginUser = clientService.getLoginUser(authToken.get());
            ...
        }
```
- Publish - Subscribe 패턴을 활용하여 SSO 서버에서 로그아웃이 일어났을때, community에서 알 수 있도록 합시다.

```java
@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public void publishMessage(String cookie_value) {

        System.out.println("publish " + cookie_value);
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                "",
                cookie_value
        );
    }

}


@Service
@Slf4j
@RabbitListener(queues = "#{yoonQueue.name}")
@RequiredArgsConstructor
public class SubscriberService {

    private final LogoutRepository logoutRepository;

    @RabbitHandler
    public void receiveMessage(String cookie_value) {
        log.info("Received: {}", cookie_value);

        CookieMsg cookieMsg = new CookieMsg();
        cookieMsg.setId(cookie_value);
        logoutRepository.save(cookieMsg);

    }


}

```

---
## 챌린지 실행화면

- community 로그인 전

![image](https://user-images.githubusercontent.com/83503188/161532552-05764e0f-003e-4b82-bb18-3cefafe0dc88.png)

- sso 로그인

![image](https://user-images.githubusercontent.com/83503188/161532765-8402a299-896c-43f7-826a-01eac1e5598b.png)

- community 로그인 후

![image](https://user-images.githubusercontent.com/83503188/161532871-ad1f9a4f-220f-42c2-91f0-2439d7b4aaa2.png)

- sso 로그아웃

![image](https://user-images.githubusercontent.com/83503188/161532895-de6aedb8-9c8b-4cc0-800f-c628d70d956e.png)

- community 로그아웃 후

![image](https://user-images.githubusercontent.com/83503188/161532922-30f97aba-09cd-4880-8a41-acbb820a2e6d.png)
