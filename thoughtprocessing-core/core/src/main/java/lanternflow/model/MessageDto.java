package lanternflow.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class MessageDto {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    //@Size(min = 2, max = 50, message = "Name is required")
    private String name;
    //@Email(message = "Email must be valid")
    private String email;
    /*@Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    @NotBlank*/
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

   // @Size(min = 5, message = "Subject must be at least 5 characters")
    private String subject;
    //@Size(min = 10, message = "Message must be at least 10 characters")
    private String message;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }





}
