package dev.aquashdw.community.controller.dto;

import dev.aquashdw.community.entity.ShopReviewEntity;

public class ShopReviewDto {
    private Long id;
    private String title;
    private String content;
    private Long shopId;
    private Long writer;
    private Long grade;

    public ShopReviewDto() {
    }

    public ShopReviewDto(ShopReviewEntity shopReviewEntity){
        this.id = shopReviewEntity.getId();
        this.title = shopReviewEntity.getTitle();
        this.content = shopReviewEntity.getContent();
        this.shopId = shopReviewEntity.getShop().getId();
        this.writer = shopReviewEntity.getWriter().getId();
        this.grade = shopReviewEntity.getGrade();
    }

    public ShopReviewDto(Long id, String title, String content, Long shopId, Long writer, Long grade) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.shopId = shopId;
        this.writer = writer;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getWriter() {
        return writer;
    }

    public void setWriter(Long writer) {
        this.writer = writer;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ShopReviewDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", shopId=" + shopId +
                ", writer=" + writer +
                ", grade=" + grade +
                '}';
    }
}
