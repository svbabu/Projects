package lanternflow.controller;

import jakarta.validation.Valid;
import lanternflow.model.Contact;
import lanternflow.model.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lanternflow.repository.ContactRepository;
import lanternflow.service.ContactService;
import lanternflow.service.EmailService;


@RestController
@RequestMapping("/thoughtprocessing-core")
public class ContactController {
    private final ContactRepository contactRepository;

    private final ContactService contactService;
    @Autowired
    private EmailService emailService;



    @Autowired
    public ContactController(ContactRepository contactRepository, ContactService contactService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
    }
    @GetMapping("/checkin")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
                .body("Success");
    }

    @GetMapping("/getContact")
    public ResponseEntity<Contact> getContact() {
       Contact contact = null;
        //contact = contactRepository.findById(1L).orElse(null);
        if(contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(contact);
    }
    /*@PostMapping("/submit")
    public ResponseEntity<String> submitContact(@RequestBody Contact contact) {
        // You can now access contact.getName(), contact.getEmail(), etc.
       com.thoughtprocessing.model.Contact contactOne = contactService.save(contact);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body("Received contact from: "
                        +contactOne.getName()+"\n"
                        +contactOne.getEmail()+"\n"
                        +contactOne.getSubject()+"\n"
                        +contactOne.getMessage());


    }*/
    @PostMapping("/submit")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<?> submitMessage(@Valid @RequestBody MessageDto message) {
        System.out.println("Received DTO: " + message);
        // convert to entity, save, etc.


        contactService.saveMessage(message);

        System.out.println("Received: " + message.getName()+"\n"+message.getEmail()
                +"\n"+message.getSubject()+"\n"+message.getMessage());
        return ResponseEntity.ok("Message saved successfully");
    }
    //email contact
    @PostMapping("/contact/send")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<?> sendContact(@RequestBody MessageDto message) {
        emailService.sendContactEmail(message);
        return ResponseEntity.ok("Message sent successfully");
    }







}
