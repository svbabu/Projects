package lanternflow.service;

import lanternflow.dto.ThoughtDTO;
import org.springframework.stereotype.Service;

@Service
public class ThoughtService {
    public String processThought(ThoughtDTO thought) {
        // You can add logging, transformation, or validation here
        return "Processed: " + thought.getTitle() + " — " + thought.getMessage();
    }


}
