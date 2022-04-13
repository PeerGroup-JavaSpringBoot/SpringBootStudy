package dev.aquashdw.community.entity;

import dev.aquashdw.community.controller.dto.ShopDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(
          targetEntity = UserEntity.class,
          fetch = FetchType.LAZY
    )
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @ManyToOne(
            targetEntity = AreaEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "located_at")
    private AreaEntity location;

    @ManyToOne(
            targetEntity = CategoryEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "category")
    private CategoryEntity categoryEntity;

    @OneToMany(
            targetEntity = ShopPostEntity.class,
            fetch = FetchType.LAZY
    )
    private List<ShopPostEntity> shopPostEntityList;

    public List<ShopPostEntity> getShopPostEntityList(){
        return this.shopPostEntityList;
    }

    public ShopEntity() {
    }

    public ShopEntity(Long id, String name, UserEntity owner, AreaEntity location, CategoryEntity categoryEntity) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.categoryEntity = categoryEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public AreaEntity getLocation() {
        return location;
    }

    public void setLocation(AreaEntity location) {
        this.location = location;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
