package lanternflow;
import lanternflow.model.Contact;
import lanternflow.repository.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thoughtprocessing-core")
@EntityScan(basePackages = "lanternflow.model")
@EnableJpaRepositories(basePackages = "lanternflow.repository")


@SpringBootApplication(scanBasePackages = {"lanternflow","lanternflow.service"})
public class Application {

    public static void main(String[] args) {
        //Dotenv dotenv = Dotenv.load();

        System.out.println("Hello from Spring Boot!");
        //System.out.println("MAIL_USERNAME: " + dotenv.get("MAIL_USERNAME"));
        // System.out.println("MAIL_PASSWORD: " + dotenv.get("MAIL_PASSWORD"));
        System.out.println("Resolved SMTP_USERNAME: " + System.getProperty("SMTP_USERNAME"));
        System.out.println("Resolved SMTP_PASSWORD: " + System.getProperty("SMTP_PASSWORD"));


        System.out.println("SECURITY_USERNAME: " + System.getenv("SECURITY_USERNAME"));
        System.out.println("SECURITY_PASSWORD: " + System.getenv("SECURITY_PASSWORD"));
        System.out.println("DB_USERNAME: " + System.getenv("DB_USERNAME"));
        System.out.println("DB_PASSWORD: " + System.getenv("DB_PASSWORD"));


        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/status")
    public String checkCoreStatus() {
        return "Lantern is glowing from core!";
    }

    @GetMapping("/check")
    public ResponseEntity<String> getValue(@RequestParam String thoughtprocessing) {
        String response = String.format("Query param: " + thoughtprocessing);
        return ResponseEntity.ok(response);
        //return ResponseEntity.ok("Query param: " + value);


    }

    /*@Bean
    CommandLineRunner init(ContactRepository repo) {
    return args -> {
     Contact c = new Contact();
    c.setName("Singu");
     c.setEmail("singu@example.com");
    c.setSubject("Lantern Inquiry");
     c.setMessage("How do we model emotional clarity in layout?");
    repo.save(c);
    };
    }*/


}
