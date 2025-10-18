package com.thoughtprocessing.enums;

public enum OfferMode implements Offer1 {

    STANDARD("Standard Offer") {
        @Override
        public String applyOffer(double price) {
            return String.format("Rs%.2f", price); // No discount
        }
    },
    SEASONAL("Seasonal Offer") {
        @Override
        public String applyOffer(double price) {
            return String.format("Rs%.2f", price * 0.85); // 15% off
        }
    },
    LIMITED("Limited Time Offer") {
        @Override
        public String applyOffer(double price) {
            return String.format("Rs%.2f", price * 0.8); // 20% off
        }
    },
    // Offer Started
    DISCOUNT("Discount Offer") {
        public  String applyOffer(double price){

            // return "Rs"+price*0.1;
            double discountedPrice = price * 0.9;//10% Off
            return String.format("Rs%.2f",discountedPrice);
        }
    },
    BUY_ONE_GET_ONE("BOGO Offer"){
        public   String applyOffer(double price){
            //double buyOnePrice = price-15; //
            //return "Offer:BUY ONE GET ONE FREE : at R"+price*0.2;
            return  String.format("Rs%.2f for 2 units",price);// Same price, double quantity
        }
    },
    CASHBACK("cashback Offer"){
        public String applyOffer(double price){
            double cashbackPrice = price -15;//Rs15 Cash Back
            //return "CashBack:Return CashBack : at R:"+(price*0.1)+":on Purchase of: at R:"+price;

            return String.format("Rs%.2f",cashbackPrice);
        }
    };

    //public  abstarct String applyOffer(double price);
    //public boolean applyOffer(double originalPrice) {
    //}
    private final String lable;
    OfferMode(String value)
    {
        lable=value;
    }

    public String getLable()
    {
        return lable;
    }

}
