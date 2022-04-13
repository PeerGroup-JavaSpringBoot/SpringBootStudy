package dev.aquashdw.community.controller.dto;

import dev.aquashdw.community.entity.ShopEntity;

import java.util.List;

public class ShopDto {
    private Long id;
    private String name;
    private Long ownerId;
    private Long location;
    private Long category;

    public ShopDto() {
    }

    public ShopDto(ShopEntity shopEntity){
        this.id = shopEntity.getId();
        this.name = shopEntity.getName();
        this.ownerId = shopEntity.getOwner().getId();
        this.location = shopEntity.getLocation().getId();
        this.category = shopEntity.getCategoryEntity().getId();
    }

    public ShopDto(Long id, String name, Long ownerId, Long location, Long category) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.location = location;
        this.category = category;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ShopDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", location=" + location +
                ", category=" + category +
                '}';
    }
}
