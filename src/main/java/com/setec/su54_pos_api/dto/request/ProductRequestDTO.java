package com.setec.su54_pos_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductRequestDTO {
    @NotBlank
    private String productName;
    @NotBlank
    private long barcode;
    @Positive
    private Double sellPrice;
    @PositiveOrZero
    private Integer quantity;
    @Positive
    private Integer categoryId;

    public @NotBlank String getProductName() {
        return productName;
    }

    public void setProductName(@NotBlank String productName) {
        this.productName = productName;
    }

    @NotBlank
    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(@NotBlank long barcode) {
        this.barcode = barcode;
    }

    public @Positive Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(@Positive Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public @PositiveOrZero Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@PositiveOrZero Integer quantity) {
        this.quantity = quantity;
    }

    public @Positive Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@Positive Integer categoryId) {
        this.categoryId = categoryId;
    }
}
