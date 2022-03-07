# Spring_Boot_Mission
[LikeLion]The Origin: Java Spring Boot 과제

### 3차 미션 스크린샷
## Basic

### 세부 조건 1
#### UserEntity 에 대한 CRUD를 작성합시다.
### User-Create 
![image](https://user-images.githubusercontent.com/83503188/156918947-d58396cd-31ab-4c7d-87e6-a747a0cc8851.png)

### User-Read-One
![image](https://user-images.githubusercontent.com/83503188/156919002-020ca5f5-36bc-4c7b-a651-b8d605f0e54e.png)

### User-Read-All
![image](https://user-images.githubusercontent.com/83503188/156918974-c29693df-a1f5-4806-9b4f-46dd41bbe580.png)

### User-Update
![image](https://user-images.githubusercontent.com/83503188/156919021-441fcfc5-3941-44ac-a2e5-ee5db2952f6f.png)
![image](https://user-images.githubusercontent.com/83503188/156919065-722aef56-2573-4ce8-8568-13d83a8b5f96.png)

### User-Delete
![image](https://user-images.githubusercontent.com/83503188/156919105-33d5a7a4-dd80-4092-a649-50822248cd4a.png)
![image](https://user-images.githubusercontent.com/83503188/156919094-a10708c2-15ff-4867-af4e-f69964d1bd76.png)

### 세부 조건 2
#### Post 를 작성하는 단계에서, User 의 정보를 어떻게 전달할지 고민해 봅시다.
### Post-Create 
> `PostDto에 userId를 넣어줌`

![image](https://user-images.githubusercontent.com/83503188/156919272-b13c2d47-c920-45f9-8bcd-cb69c975bba0.png)
![image](https://user-images.githubusercontent.com/83503188/155079380-49c87933-fd7c-48b7-a580-36cbe4501406.png)
#### User - Post생성 후 User에 반영
![image](https://user-images.githubusercontent.com/83503188/156919357-c23b351a-e7b5-46a3-9188-5fa8ba767ff5.png)

## Challenge

### 세부 조건 1
#### 생성된 테이블의 실제 이름에는 Entity 라는 문구가 들어가지 않도록 @Table 어노테이션을 활용합시다.
### AreaEntity 
```
@Entity
@Getter @Setter
@Table(name = "AREA")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Embedded
    private Location location;
}
```
```
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String province;

    private String city;

    private String street;
}
```


```
@Embeddable
@Getter
public class Location {
    private Long latitude;
    private Long longtitude;
}

```

### UserEntity
> `verify 변수를 통해 일반 사용자와 상점 사용자를 구분`

```
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area")
    private Area area;

    private Boolean verify;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Shop> shopList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ShopPost> shopPosts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShopReview> shopReviews;

    public User(UserDto userDto) {
        this.name = userDto.getName();
        this.verify = userDto.getVerify();
    }

    public void update(UserDto userDto) {
        this.name = userDto.getName();
    }

    public void addShop(Shop shop) {
        shopList.add(shop);
        shop.setUser(this);
    }

    public void addPost(Post post) {
        postList.add(post);
        post.setUser(this);
    }

    public void addShopPost(ShopPost shopPost) {
        shopPosts.add(shopPost);
        shopPost.setUser(this);
    }

    public void addShopReview(ShopReview shopReview) {
        shopReviews.add(shopReview);
        shopReview.setUser(this);
    }

    public void addArea(Area area) {
        this.area = area;
    }
}
```

### ShopEntity
> `Shop에서 취급하는 품목에 대한 Cateogory를 자바의 Enum클래스를 통해 처리`

> `지역을 나타내기 위해 Shop의 필드에 AreaEntity를 추가`

> `일반 사용자의 Shop생성을 막기 위해 ShopService에서 verify 변수를 검사`
```
@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "SHOP")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Address address;

    // orphanRemoval = true : 고아가 된 자식 엔티티 자동 삭제
    // CascadeType.all : 연관관계가 하나인 경우만 가능
    // shoppost,shopreview 두개가 있으므로 불가
    @OneToMany(mappedBy = "shop",orphanRemoval = true)
    private List<ShopPost> shopPosts;

    @OneToMany(mappedBy = "shop",orphanRemoval = true)
    private List<ShopReview> shopReviews;


    public Shop(ShopDto shopDto) {
        this.category = shopDto.getCategory();
        this.address = shopDto.getAddress();
    }

    public void addShopPost(ShopPost shopPost) {
        this.shopPosts.add(shopPost);
        shopPost.setShop(this);
    }


    public void addShopReview(ShopReview shopReview) {
        this.shopReviews.add(shopReview);
        shopReview.setShop(this);
    }

    public void update(ShopDto shopDto) {
        this.category = shopDto.getCategory();
        this.address = shopDto.getAddress();
    }
}
```

```
public enum Category {
    FOOD, CLOTHES, SHOES
}
```

```
public ShopDto createShop(ShopDto shopDto) {

        Long userId = shopDto.getUserId();
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty() || optionalUser.get().getVerify() == false)
            return null;

        User user = optionalUser.get();
        Shop shop = new Shop(shopDto);
        user.addShop(shop);

        shopRepository.save(shop);
        return shopDto;
    }
```
### ShopPostEntity
> `Shop의 주인만 ShopPost를 작성할 수 있도록 ShopPostService에서 주인 확인`

```
public ShopPostDto createShopPost(Long shopId, ShopPostDto shopPostDto) {

        Optional<User> optionalUser = userRepository.findById(shopPostDto.getUserId());
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalUser.isEmpty() || optionalShop.isEmpty())
            return null;

        shopPostDto.setShopId(shopId);

        User user = optionalUser.get();
        Shop shop = optionalShop.get();

        // 본인 가게에 대한 예외처리
        if (shop.getUser() != user)
            return null;

        ShopPost shopPost = new ShopPost(shopPostDto);
        shop.addShopPost(shopPost);
        user.addShopPost(shopPost);

        shopPostRepository.save(shopPost);

        return shopPostDto;
    }
```

### 세부 조건 2 
#### 변동될 가능성이 있는 데이터와 변동될 가능성이 없는 데이터를 잘 구분하여, Entity 작성 여부를 잘 판단합시다.
> `Shop의 update의 경우 기존 사용자를 변경할 수 없도록함`

```
public boolean updateShop(Long shopId, ShopDto shopDto) {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (optionalShop.isEmpty())
            return false;

        Shop shop = optionalShop.get();
        // 기존 사용자가 아닐 경우 예외
        if (shopDto.getUserId() != shop.getUser().getId())
            return false;


        shop.update(shopDto);
        return true;
    }
```

### 세부 조건 3
#### Entity 를 먼저 구성하되, 시간이 남으면 CRUD까지 구성해 봅시다.

### User-Create
![image](https://user-images.githubusercontent.com/83503188/156919938-065a6758-adec-45f2-87c2-f317dd21feb8.png)

### Shop-Create
![image](https://user-images.githubusercontent.com/83503188/156919965-80488c4e-dc84-42f1-9c57-906c813e6da0.png)
### Shop-Post-Create
![image](https://user-images.githubusercontent.com/83503188/156919992-25b4ba18-90f9-4765-97f2-ddb63caf1862.png)
### Shop-Post-Read
![image](https://user-images.githubusercontent.com/83503188/156920007-e74490f0-f44a-41b4-ab29-083b3cd91da5.png)

### Shop-Post-Update
![image](https://user-images.githubusercontent.com/83503188/156920020-300b5735-1511-41f4-a72c-bddfcb770326.png)
![image](https://user-images.githubusercontent.com/83503188/156920031-82526186-5ad2-45f0-97c6-67bc670f47cf.png)

### Shop-Post-Delete
![image](https://user-images.githubusercontent.com/83503188/156920056-25d75e8d-87e2-438e-ad5a-25e89e5c904f.png)
![image](https://user-images.githubusercontent.com/83503188/156920063-16f41423-b39c-4f59-abce-dcb7ee788332.png)

### Shop-Review-Create
![image](https://user-images.githubusercontent.com/83503188/156920083-2efdf01b-c71e-4d00-88a7-9040b95aaf58.png)

#### Shop삭제 후 Shop-Post와 Shop-Review
> `Shop을 삭제하면 Shop에 포함된 ShopPost와 ShopReview모두 삭제`

![image](https://user-images.githubusercontent.com/83503188/156920114-eff03863-5388-4e58-ba56-0bfd68465fbd.png)

![image](https://user-images.githubusercontent.com/83503188/156920151-2fc85e66-8216-4aa2-a000-f01ca2b57730.png)

![image](https://user-images.githubusercontent.com/83503188/156920161-e3de9907-d36d-416a-9136-2e23c92f86d1.png)

![image](https://user-images.githubusercontent.com/83503188/156920173-550026eb-3787-496c-a9d8-ddd5e2d87188.png)




