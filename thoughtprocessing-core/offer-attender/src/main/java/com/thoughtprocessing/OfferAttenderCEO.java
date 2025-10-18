package com.thoughtprocessing;

import com.thoughtprocessing.service.OfferSummaryMerger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.thoughtprocessing.model")
@EnableJpaRepositories(basePackages = "com.thoughtprocessing.repository")


@SpringBootApplication(scanBasePackages = {"com.thoughtprocessing"})
 @ComponentScan(basePackages = {"com.thoughtprocessing.security",
         "com.thoughtprocessing.controller","com.thoughtprocessing.service",
         "com.thoughtprocessing.dto"
     })

/*@SpringBootApplication*/
public class OfferAttenderCEO

{
    public static void main( String[] args )
    {
        System.out.println("Hello from  Spring Boot OfferAttenderCEO !");
        SpringApplication.run(OfferAttenderCEO.class, args);

        //core main logic
        // to see how IntelliJ IDEA suggests fixing it.
        /*System.out.println("Welcome to the Cusomer!");
        System.out.println("=== OFFER SUMMARY ===");
        ProductPage.showOffer("mobile Phone");
        //enum
        double originalPrice = 500.0;

        for (OfferMode mode : OfferMode.values()) {
            System.out.println("Mode:" + mode);
            System.out.println(mode.applyOffer(originalPrice));
            System.out.println("=== OFFER SUMMARY ===");
        }
        //
        OfferEngine prodOfferEngine = new OfferEngine();
        System.out.println(prodOfferEngine.applyAllOffers("Wireless Mouse", 1200) + "\n");

        OfferEngine homecntrengine = new OfferEngine();
        System.out.println(homecntrengine.applyHmeCntrAllItems("F01", "Furnuture and Decor"));

        //Offer1 offerMode=new OfferMode()
        //offerValidatorService
        OfferValidatorService offerValidatorService = new OfferValidatorService();
        String productName = "Elegant Mug";
        double price = 299.0;
        double discountPrice = 249.0;
        for (OfferMode mode : OfferMode.values()) {

            boolean isValid=offerValidatorService.isValidOffer(mode,productName,price,discountPrice);
            System.out.println("OfferMode:" + mode+"->isValid:"+isValid);

        }
               String isvalidOfferSummary=offerValidatorService.isValidOfferSummary(productName, price, discountPrice);
               System.out.println("isvalidOfferSummary:"+isvalidOfferSummary);


        Map<OfferMode,Boolean> offerStatus=offerValidatorService.evaluateOfferModes(productName,price,discountPrice);
        for(OfferMode mode : OfferMode.values()){
           Boolean isValid=offerValidatorService.isValidOffer(mode,productName,price,discountPrice);
           offerStatus.put(mode,isValid);
            System.out.println("offerStatus:"+isValid);
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
*/
       // OfferSummaryMerger.run();
    }





}
