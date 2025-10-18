package com.thoughtprocessing.validation;

import com.thoughtprocessing.enums.Offer1;
import com.thoughtprocessing.enums.OfferMode;

import java.util.Map;

public class ValidationReport {
    private final boolean productNameValid;
    private final boolean priceValid;
    private final boolean discountValid;
    private final Offer1 offerMode;
    private final String summary;

    //constructor
    private ValidationReport(
            boolean ProductNamevalid,
            boolean Pricevalid,
            boolean Discountvalid, Offer1 offerMode, String summary) {
        this.productNameValid = ProductNamevalid;
        this.priceValid = Pricevalid;
        this.discountValid = Discountvalid;
        this.offerMode = offerMode;
        this.summary = summary;

    }

    //Getters
    public boolean isProductNamevalid() {
        return productNameValid;
    }

    public boolean isPricevalid() {
        return priceValid;
    }

    public boolean isDiscountvalid() {
        return discountValid;
    }

    public Offer1 getOfferMode() {
        return offerMode;
    }

    public String getSummary() {
        return summary;
    }

    //Option:Builder for modular Construction
    public static class Builder {
        private  boolean productNameValid = true;
        private  boolean priceValid = true;
        private  boolean discountValid = true;
        private  Offer1 offerMode = OfferMode.STANDARD;
        //extra config
        private static final Map<Offer1, Boolean> discountValidityMap = Map.of(
                OfferMode.STANDARD, false,
                OfferMode.LIMITED, true,
                OfferMode.DISCOUNT, true,
                OfferMode.SEASONAL, true
        );
        private String summary = "Validation passed";


        public Builder productNameValid(boolean value) {
            this.productNameValid = true;
            return this;
        }

        public Builder withPricevalid(boolean value) {
            this.priceValid = value;
            return this;
        }

        public Builder withDiscountvalid(boolean value) {
            this.discountValid = value;
            return this;
        }

        public Builder withOfferMode(Offer1 mode) {
            this.offerMode = mode;
            // discountValid=(mode != OfferMode.STANDARD);
            this.discountValid=discountValidityMap.getOrDefault(mode,false);
            return this;
        }

        public Builder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public ValidationReport build() {
            return new ValidationReport(productNameValid, priceValid, discountValid, offerMode, summary);
        }
    }
}
