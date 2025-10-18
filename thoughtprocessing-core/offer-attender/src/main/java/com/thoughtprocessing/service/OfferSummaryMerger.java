package com.thoughtprocessing.service;

import com.thoughtprocessing.enums.OfferMode;
import com.thoughtprocessing.validation.ValidationReport;

import java.util.Map;

public class OfferSummaryMerger {
    public static void run() {
        // Merge logic here
        // Parse offer blocks, validate modes, print summaries
        displayWelcomeMessage();
        showProductOfferModes();
        applyOfferEngines();
        validateOfferModes();
        summarizeValidationReport();
    }
    private static void displayWelcomeMessage() {

        System.out.println("Welcome to the Cusomer!");
        System.out.println("=== OFFER SUMMARY ===");
        ProductPage.showOffer("mobile Phone");
    }
    //enum
    private static void showProductOfferModes() {
        System.out.println("!!Showing product offers modes!!");
        double originalPrice = 500.0;

        for (OfferMode mode : OfferMode.values()) {
            System.out.println("Mode:" + mode);
            System.out.println(mode.applyOffer(originalPrice));
            System.out.println("=== OFFER SUMMARY ===");
        }
    }
    //
    private static void applyOfferEngines() {
        System.out.println("!!Applying offer engines!!");
        OfferEngine prodOfferEngine = new OfferEngine();
        System.out.println(prodOfferEngine.applyAllOffers("Wireless Mouse", 1200) + "\n");

        OfferEngine homecntrengine = new OfferEngine();
        System.out.println(homecntrengine.applyHmeCntrAllItems("F01", "Furnuture and Decor"));
    }
    //Offer1 offerMode=new OfferMode()
    //offerValidatorService
    private static void validateOfferModes() {
        System.out.println("!!Validating offer modes!!");
        OfferValidatorService offerValidatorService = new OfferValidatorService();
        String productName = "Elegant Mug";
        double price = 299.0;
        double discountPrice = 249.0;
        for (OfferMode mode : OfferMode.values()) {

            boolean isValid = offerValidatorService.isValidOffer(mode, productName, price, discountPrice);
            System.out.println("OfferMode:" + mode + "->isValid:" + isValid);

        }
    }
    //Offer Summary Validator
    private static void  summarizeValidationReport() {
        System.out.println("!!Summarizing validation report!!");

        OfferValidatorService offerValidatorService = new OfferValidatorService();
        String productName = "Elegant Mug";
        double price = 299.0;
        double discountPrice = 249.0;


        String isvalidOfferSummary = offerValidatorService.isValidOfferSummary(productName, price, discountPrice);
        System.out.println("isvalidOfferSummary:" + isvalidOfferSummary);


        Map<OfferMode, Boolean> offerStatus = offerValidatorService.evaluateOfferModes(productName, price, discountPrice);
        for (OfferMode mode : OfferMode.values()) {
            Boolean isValid = offerValidatorService.isValidOffer(mode, productName, price, discountPrice);
            offerStatus.put(mode, isValid);
            System.out.println("offerStatus:" + isValid);
        }


        //ValidationReport
        ValidationReport report = new ValidationReport.Builder()
                .productNameValid(true)
                .withPricevalid(true)
                .withDiscountvalid(false)
                .withOfferMode(OfferMode.STANDARD)
                .withSummary("Validation complete")
                .build();
        System.out.println(report.getSummary());
    }
}
