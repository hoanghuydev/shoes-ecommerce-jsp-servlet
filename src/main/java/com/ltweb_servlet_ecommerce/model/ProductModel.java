package com.ltweb_servlet_ecommerce.model;

public class ProductModel extends  AbstractModel<ProductModel> {
    private String name;
    private String content;
    private String shortDescription;
    private String thumbnail;
    private Double price;
    private String modelUrl;
    private String slug;
    private  Long categoryId;
    private Integer totalViewAndSearch;

    public Integer getTotalViewAndSearch() {
        return totalViewAndSearch;
    }

    public void setTotalViewAndSearch(Integer totalViewAndSearch) {
        this.totalViewAndSearch = totalViewAndSearch;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


}
