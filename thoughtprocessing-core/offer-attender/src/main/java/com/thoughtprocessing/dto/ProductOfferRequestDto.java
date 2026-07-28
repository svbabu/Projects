package com.thoughtprocessing.dto;

public class ProductOfferRequestDto {
    private Long productId;
    private Long offerId;
    public ProductOfferRequestDto() {}

    public ProductOfferRequestDto(Long productId, Long offerId) {
        this.productId = productId;
        this.offerId = offerId;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}
