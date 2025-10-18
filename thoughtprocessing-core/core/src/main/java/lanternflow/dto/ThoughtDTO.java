package lanternflow.dto;

public class ThoughtDTO {

    private String title;
    private String message;

    public ThoughtDTO() {
        // Default constructor for deserialization
    }

    public ThoughtDTO(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ThoughtDTO{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
