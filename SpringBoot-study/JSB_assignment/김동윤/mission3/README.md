0) local 환경(개발환경), prod(상용환경) 분리

![](https://images.velog.io/images/myway00/post/e5bae34a-5182-4161-93a0-0d562380301f/image.png)

# 1) UserEntity - PostEntity (1:多 관계)
## 1-1 ) UserEntity 생성
```java
public class UserEntity {
    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(
            targetEntity = PostEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "userentity"
    )
    private List<PostEntity> postentitylist = new ArrayList<>();

```

## 1-2 ) PostEntity 에 User와의 관계 맵핑
```java
    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name="user_id")
    private UserEntity userentity;
```

### 1-3 ) Repository & DTO
- User,Post 레포지토리 둘다 각각의 엔티티에 맞춰서 CrudRepository 상속받음
- Post에 `List<PostEntity> findAllByUserId(Long id);` 추가해서 userid에 해당하는 포스트를 다 찾아오게 하는 아이 하나 선언함 (User Entity가 갖고 있는 포스트 목록 저장할 때 사용할 예정)

### 1-3 ) PostDao 로 로직 구성
- post를 보낼 때 header에서 user 정보를 받아오도록 구성
(실제 서비스에서는 로그인 된 유저가 있으면 header에 유저 아이디를 담아놓을 수 있을 것이라 가정)

- `PostController`
=> Controller에서 헤더를 통해서 user의 정보를 받아오면
![](https://images.velog.io/images/myway00/post/a6debbe4-8913-4df5-8bd5-f3b068bc1855/image.png)

- `PostDao`
=> 헤더 아이디로 userentity를 데려오고 post에 저장한다
=> writer도 해당하는 아이디의 userentity의 name으로 자동 저장한다.

![](https://images.velog.io/images/myway00/post/7016b5e4-60fc-4a36-9580-30dd38c53492/image.png)

=> PostMan 시험 방식
![](https://images.velog.io/images/myway00/post/3440508e-a388-4bf3-b717-c3b7f1dc6244/image.png)

![](https://images.velog.io/images/myway00/post/ce9545e6-0ea3-4a56-ae38-29ebfe4aa89a/image.png)

이런 식으로 요청을 보내면

![](https://images.velog.io/images/myway00/post/653165c6-0ee8-4602-b3e8-861bc1aa209a/image.png)

- 위와 같이 userentity 잘 인식한다 (따로 writer 지정안했는데 지가 알아서 header 에서 받은 user id 정보로 해당 user id에 속하는 entity의 name을 가져다가 writer로 잘 지정함)