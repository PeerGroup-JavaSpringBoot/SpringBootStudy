# Chapter 6: 상품 등록 및 조회하기

## 상품 등록하기



```java
@Getter
@Table(name = "item_img")
@Entity
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private Boolean repImg;

    @ManyToOne(fetch = FetchType.LAZY) // 1)
    @JoinColumn(name = "item_id")
    private Item item; 

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) { // 2)
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
    
}
```

1. 상품 엔티티와 다대일 단뱡항 관계로 매핑, 지연 로딩을 설정하여 매핑된 상품 엔티티 정보가 필요한 경우 데이터를 조회하도록 한다.
2. 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력 받아서 이미지 정보를 업데이트하는 메소드

상품을 등록할 때는 화면으로부터 전달받은 DTO 객체를 엔티티 객체로 변환하는 작업을 해야 하고, 상품을 조회할 때는 엔티티 객체를 DTO 객체로 바꿔주는 작업을 해야한다. 멤버 변수가 몇 개 없다면 금방 할 수도 있지만 멤버 변수가 많아진다면 상당한 시간을 소모한다.

엔티티 <-> DTO 서로 반환해주는 반복적인 작업을 도와주는 라이브러리로 modelmapper가 있다. 해당 라이브러리는 서로 다른 클래스의 값을 필드의 이름과 자료형이 같으면 getter, setter를 통해 값을 복사해서 객체를 반환해준다.

```java
@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private Boolean repImg;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImage) {
        return modelMapper.map(itemImage,ItemImgDto.class);
    }

}
```

상품 등록 같은 관리자 페이지에서 중요한 것은 데이터의 무결성을 보장해야 한다는 것이다. 데이터가 의도와 다르게 저장된다거나, 잘못된 값이 저장되지 않도록 밸리데이션(validation)을 해야 한다. 
특히 데이터끼리 서로 연관이 되어 있으면 어떤 데이터가 변함에 따라서 다른 데이터도 함께 체크를 해야 하는 경우가 많다.


![image](https://user-images.githubusercontent.com/83503188/169641399-37e0cb32-488a-4cc9-8016-1ea28cf8cc57.png)

### 이미지 파일에 대한 설정 

이미지 파일을 등록할 때 서버에서 각 파일의 최대 사이즈와 한번에 다운 요청할 수 있는 파일의 크기를 지정할 수 있다. 또한 컴퓨터에서 어떤 경로에 저장할지를 관리하기 위해서 프로퍼티에 itemImgLocation을 추가한다.

```java
  servlet:
    multipart:
      max-file-size: 10MB # 파일 업로드 요청 시 하나의 파일 크기 10MB로 제한
      max-request-size: 100MB # 파일 업로드 요청 시 모든 파일의 크기의합 100MB로 제한

file:
  upload:
    path: /C:/Users/dudwl/temp/
```

### 업로드한 파일을 읽어올 경로를 설정

WebMvcConfigurer 인터페이스를 구현하는 WebMvcConfig.java 파일을 작성한다. addResourceHandlers 메소드를 통해서 자신의 로컬 컴퓨터에 업로드한 파일을 찾을 위치를 설정한다.

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}") // 1)
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/images/**")   // 2)
                .addResourceLocations("file://" + uploadPath); // 3)
    }

}
```

1. application.yml에 설정한 경로를 불러온다.
2. 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 `file.upload.path`에 설정한 폴더를 기준으로 파일을 읽어오도록 설정
3. 로컬 컴퓨터에 저장된 파일을 읽어올 root경로를 설정한다.

### 파일을 처리하는 FileService 

```java
@Slf4j
@Service
public class FileService {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public String getFullFileUploadPath(String filename) {
        return fileUploadPath + filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return new UploadFile("", "", "");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String fileUploadUrl = getFullFileUploadPath(storeFileName);
        multipartFile.transferTo(new File(getFullFileUploadPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName, fileUploadUrl);

    }

    private String  createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    public void deleteFile(String fileUploadUrl) {
        File deleteFile = new File(fileUploadUrl);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}
```

### 상품 등록하는 AdminItemService

```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemService itemService;
    private final MemberService memberService;
    private final ItemImageService itemImageService;

    @Transactional
    public Item saveItem(ItemFormDto dto, String email) throws IOException {

        // 회원 조회
        Member member = memberService.getMemberByEmail(email);

        // 상품 등록
        Item item = dto.toEntity();
        Item saveItem = Item.createItem(item, member);
        saveItem = itemService.saveItem(saveItem);

        // 상품 이미지 등록
        itemImageService.saveItemImages(saveItem, dto.getItemImageFiles());

        return saveItem;
    }

}
```

### 상품을 등록하는 AdminItemController

```java
@Slf4j
@Controller
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemController {

    private final AdminItemService adminItemService;
    
    ...

    @PostMapping("/new")
    private String itemNew(
            @Valid @ModelAttribute("itemFormDto") ItemFormDto dto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            RedirectAttributes redirectAttributes
    ) {

        if (dto.getItemImageFiles().get(0).isEmpty()) { // 1)
            bindingResult.reject("requiredFirstItemImage", ErrorCode.REQUIRED_REPRESENT_IMAGE.getMessage());
            return "adminitem/registeritemform";
        }
        if (bindingResult.hasErrors()) { // 2)
            return "adminitem/registeritemform";
        }

        String email = userDetails.getUsername();
        try {
            Item savedItem = adminItemService.saveItem(dto, email);
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject("globalError", "상품 등록 중 에러가 발생하였습니다.");
            return "adminitem/registeritemform";
        }

        return "redirect:/";
    }

}
```

1. 상품 등록 시 첫 번째 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환한다. 상품의 첫 번째 이미지는 메인 페이지에서 보여줄 상품 이미지로 사용하기 위해서 필수 값으로 지정
2. 상품 등록 시 DTO에 정의한 Validation에 어긋나는 경우 다시 상품 등록 페이지로 전환한다.

## 상품 수정하기

### AdminItemController 

```java
@Slf4j
@Controller
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class AdminItemController {

    private final AdminItemService adminItemService;

    ...

    @GetMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId, Model model) {
        try {
            UpdateItemDto updateItemDto = adminItemService.getUpdateItemDto(itemId);
            model.addAttribute("updateItemDto", updateItemDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("updateItemDto", new UpdateItemDto());
            return "adminitem/updateitemform";
        }

        return "adminitem/updateitemform";
    }

    @PostMapping("/{itemId}")
    public String itemEdit(
            @PathVariable Long itemId,
            @Valid UpdateItemDto updateItemDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (updateItemDto.getItemImageFiles().get(0).isEmpty() &&
                !StringUtils.hasText(updateItemDto.getOriginalImageNames().get(0))) {
            bindingResult.reject("requiredInputFirstImage", ErrorCode.REQUIRED_REPRESENT_IMAGE.getMessage());
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        } else if (bindingResult.hasErrors()) {
            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);
            return "adminitem/updateitemform";
        }

        try {
            adminItemService.updateItem(updateItemDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            bindingResult.reject("globalError", "상품 등록 중 에러가 발생하였습니다.");
            return "adminitem/updateitemform";
        }

        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/";

    }

}
```

### AdminItemService

```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemService itemService;
    private final MemberService memberService;
    private final ItemImageService itemImageService;

    ...

    public UpdateItemDto getUpdateItemDto(Long itemId) {
        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(itemId);
        Item item = itemImages.get(0).getItem();
        return UpdateItemDto.of(item, itemImages);
    }

    public List<UpdateItemDto.ItemImageDto> getItemImageDtos(Long itemId) {

        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(itemId);
        return UpdateItemDto.ItemImageDto.of(itemImages);

    }


    @Transactional
    public void updateItem(UpdateItemDto updateItemDto) throws IOException {

        // 상품 업데이트
        updateItemInfo(updateItemDto);

        // 상품 이미지 업데이트
        updateItemImages(updateItemDto);

    }

    private void updateItemInfo(UpdateItemDto updateItemDto) {
        Item updateItem = updateItemDto.toEntity();
        itemService.updateItem(updateItemDto.getItemId(), updateItem);
    }

    private void updateItemImages(UpdateItemDto updateItemDto) throws IOException {

        // 데이터베이스에 저장된 상품 이미지 정보
        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(updateItemDto.getItemId());
        List<String> originalImageNames = updateItemDto.getOriginalImageNames(); // 상품 수정 화면 조회 시에 있던 상품 이미지명 정보
        List<MultipartFile> itemImageFiles = updateItemDto.getItemImageFiles(); // 상품 파일 이미지 정보


        for (int i = 0; i < itemImages.size(); i++) {
            ItemImage itemImage = itemImages.get(i);
            String originalImageName = originalImageNames.get(i);
            MultipartFile itemImageFile = itemImageFiles.get(i);

            if (!itemImageFile.isEmpty()) {  // 기존 파일 수정 or 신규 파일 등록 처리
                itemImageService.updateItemImage(itemImage, itemImageFile);
            } else if (!StringUtils.hasText(originalImageName) &&
                    StringUtils.hasText(itemImage.getOriImgName())) { // 기존 파일 삭제
                itemImageService.deleteItemImage(itemImage);
            }
        }
    }
}
```

## 상품 관리하기 

등록된 상품 리스트를 조회할 수 있는 화면

상품 관리 화면에서는 상품을 조회하는 조건을 설정 후 페이징 기능을 통해 일정 개수의 상품만 불러오며, 선택한 상품 상세 페이지로 이동할 수 있는 기능까지 구현한다. 조회 조건으로 설정할 값은 다음과 같다.

- 상품 등록일
- 상품 판매 상태
- 상품명 또는 상품 등록자 아이디

조회 조건이 복잡한 화면의 경우 QueryDsl을 이용해 조건에 맞는 쿼리를 동적으로 쉽게 생성할 수 있다. 

```java
@Getter
@Setter
public class ItemSearchDto {

    private String searchDateType; // 1)

    private ItemSellStatus searchSellStatus; // 2)

    private String searchBy; // 3)

    private String searchQuery = ""; // 4)

}
```

1. 현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회한다.
2. 상품의 판매상태를 기준으로 상품 데이터를 조회한다.
3. 상품을 조회할 때 어떤 유형으로 조회할지 선택한다. -> 상품명 or 상품 등록자 아이디
4. 조회할 검색어를 저장할 변수

### 사용자 정의 인터페이스 작성 

```java
public interface ManageItemRepository {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}

```

### 사용자 정의 인터페이스 구현

주의할 점으로는 클래스명 끝에 "Impl"을 붙여주어야 정상적으로 동작한다.

```java
public class ManageItemRepositoryImpl implements ManageItemRepository {

    private JPAQueryFactory queryFactory;

    public ManageItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {

        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("itemNm", searchBy)) {
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) { // 1)

        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .fetch().size();

        return new PageImpl<>(content, pageable, totalSize);
    }
}
```

1. queryFactory를 이용해서 쿼리를 생성한다. 쿼리문을 직접 작성할 때의 형태와 문법이 비슷한 것을 볼 수 있다.
   1. selectFrom(QItem.item): 상품 데이터를 조회하기 위해서 QItem의 item을 지정한다.
   2. where 조건절: BooleanExpression을 반환하는 조건문들을 넣어준다. ',' 단위로 넣어줄 경우 and 조건으로 인식한다.
   3. offset: 데이터를 가지고 올 시작 인덱스를 지정한다.
   4. limit: 한 번에 가지고 올 최대 개수를 지정한다.


### Spring Data Jpa 레포지토리에서 사용자 정의 인터페이스 상속

ItemRepository에서 QueryDsl로 구현한 상품 관리 페이지 목록을 불러오는 getAdminItemPage() 메소드를 사용할 수 있다.

```java
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ManageItemRepository {

    ...
}
```

### ManageItemController 클래스

```java
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")

public class ManageItemController {

    private final ManageItemService manageItemService;

    @GetMapping(value = {"/manage", "/manage/{page}"}) // 1)
    public String itemManage(ItemSearchDto itemSearchDto,
                             @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> items = manageItemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "manageitem/itemMng";

    }

}
```

1. value에 상품 관리 화면 진입 시 URL에 페이지 번호가 없는 경우와 페이지 번호가 있는 경우 2가지를 매핑한다. 


## 메인 화면

QueryDsl을 사용하여 페이징 처리 및 네비게이션바에 있는 Search 버튼을 이용하여 상품명으로 검색이 가능하도록 구현

### MainItemDto

```java
@Builder
@Getter
@Setter
public class MainItemDto {


    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private Integer price;

    @QueryProjection // 1)
    public MainItemDto(Long id, String itemName, String itemDetail, String imageUrl, Integer price) {
        this.itemId = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static MainItemDto toEntity(Item item, ItemImage itemImage) {
        return MainItemDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .price(item.getPrice())
                .imageUrl(itemImage.getImgUrl())
                .build();
    }


}
```

1. 생성자에 @QueryProjection 어노테이션을 선언하여 Querydsl로 결과 조회 시 MainItemDto 객체로 바로 받아올 수 있다.

### 사용자 정의 인터페이스

```java
public interface MainItemImageRepository {

    Page<MainItemDto> findMainItemDto(ItemSearchDto itemSearchDto, Pageable pageable);

}

@Repository
public class MainItemImageRepositoryImpl implements MainItemImageRepository {

   private JPAQueryFactory queryFactory;

   public MainItemImageRepositoryImpl(EntityManager em) {
      this.queryFactory = new JPAQueryFactory(em);
   }

   private BooleanExpression searchByLike(String searchQuery) {

      return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%"); // 1)

   }

   public Page<MainItemDto> findMainItemDto(ItemSearchDto itemSearchDto, Pageable pageable) {
      QItem qItem = QItem.item;
      QItemImage qItemImage = QItemImage.itemImage;

      List<MainItemDto> results = queryFactory
              .select(
                      new QMainItemDto( // 2)
                              qItem.id,
                              qItem.itemNm,
                              qItem.itemDetail,
                              qItemImage.imgUrl,
                              qItem.price
                      )
              )
              .from(qItemImage)
              .join(qItemImage.item, qItem) // 3)
              .where(
                      qItem.stockNumber.ne(0),
                      qItem.itemSellStatus.ne(ItemSellStatus.SOLD_OUT),
                      qItemImage.isRepImg.eq(true),
                      searchByLike(itemSearchDto.getSearchQuery())
              )
              .orderBy(qItem.id.desc())
              .offset(pageable.getOffset())
              .limit(pageable.getPageSize())
              .fetch();

      int totalSize = queryFactory
              .selectFrom(qItem)
              .where(
                      qItem.stockNumber.ne(0),
                      qItem.itemSellStatus.ne(ItemSellStatus.SOLD_OUT),
                      searchByLike(itemSearchDto.getSearchQuery())
              )
              .fetch().size();

      return new PageImpl<>(results, pageable, totalSize);
   }
}
```

1. 검색어가 null이 아니면 상품며에 해당 검색어가 포함되는 상품을 조회하는 조건을 반환한다.
2. QMainItemDto의 생성자에 반환할 값들을 넣어준다. @QueryProjection을 사용하면 DTO로 바로 조회가 가능하다.
3. ItemImage와 Item을 내부 조인한다.

## 상품 상세 페이지

메인 페이지에서 상품 이미지나 상품 정보를 클릭 시 상품의 상세 정보를 보여주는 페이지를 구현, 상품 상세페이지ㅣ에서는 주문 및 장바구니 추가 기능을 제공하며 해당 내용은 7장에서 구현한다.

```java
@Getter
@Setter
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();


    @Builder
    public ItemDtlDto(
            Long itemId, String itemName, Integer price, String itemDetail, Integer stockNumber,
            ItemSellStatus itemSellStatus, List<ItemImageDto> itemImageDtos) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.itemImageDtos = itemImageDtos;
    }

    public static ItemDtlDto of(Item item, List<ItemImage> itemImages) {

        List<ItemImageDto> itemImageDtos = ItemImageDto.of(itemImages);

        return ItemDtlDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .itemImageDtos(itemImageDtos)
                .build();
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class ItemImageDto {
        private String imageUrl;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream().map(
                    itemImage -> ItemImageDto.builder()
                            .imageUrl(itemImage.getImgUrl())
                            .build()).collect(Collectors.toList());
        }
    }
}
```

---

## Fetch Join 

### 현재 상황 

![image](https://user-images.githubusercontent.com/83503188/169695196-44d900c3-1a2e-457f-a7db-ea855c3744ad.png)

상품아이디(Item->id), 상품명(Item->itemNm), 상태(Item->itemSellStatus), 등록자(Item->createdBy), 등록일(Item->regTime)

모두 Item에서 가져올 수 있는 데이터 -> Item과 연관된 Member를 조회할 필요가 없음

따라서 지연로딩으로 설정하였기 때문에 SELELT SQL에는 item에 관한 쿼리문만 존재한다.

![image](https://user-images.githubusercontent.com/83503188/169695429-1faae19e-b8e5-4de9-bd4b-f575bf5a38b1.png)

**등록자 이름(Item -> Member -> name)**과 같은 Item에 연관된 필드에서 데이터를 조회해야 하는 경우에는?

### 변경된 상황

![image](https://user-images.githubusercontent.com/83503188/169695570-6f9c0662-6e3c-4452-a01b-cb6880feb14c.png)

![image](https://user-images.githubusercontent.com/83503188/169695598-2b80e690-ec74-4e1e-9689-2954df465a0a.png)

지연로딩으로 설정하였기 때문에 SELECT SQL에 Item과 연관된 Member를 조회하기 위한 쿼리문이 하나 더 생긴걸 확인할 수 있다.

만약 연관된 데이터가 굉장히 많고 모든 연관된 데이터에서 데이터를 조회해야 한다면 연관된 데이터의 개수만큼 SELECT SQL이 추가될 것이다. 

### Fetch Join 적용하기

```java
public class ManageItemRepositoryImpl implements ManageItemRepository {
    
   ...
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                /**
                 * fetch join 적용 
                 */
                .join(QItem.item.member, QMember.member).fetchJoin() 
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .fetch().size();

        return new PageImpl<>(content, pageable, totalSize);
    }
}
```

![image](https://user-images.githubusercontent.com/83503188/169695763-b4eac364-cac9-40b5-9c3a-0e630e5cb9e8.png)

QueryDsl에 fetch join을 적용하고 실행하면 하나의 SELECT SQL안에 inner join을 통해서 Item과 연관된 Member를 한 번에 가져옴을 볼 수 있다.



