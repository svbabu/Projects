package lanternflow.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thoughtprocessing-core")
public class CoreController {


   // private final ThoughtService thoughtService;

    //public CoreController(ThoughtService thoughtService) {
       // this.thoughtService = thoughtService;
   // }



    @GetMapping("/test")
    public String glow() {
        return "Lantern is glowing from core!";
    }


    @GetMapping("/testone")
    public ResponseEntity<String> getValue(@RequestParam String value) {
        return ResponseEntity.ok("Query param: " + value);


    }

  /*  @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody ThoughtDTO dto) {

        String result = thoughtService.processThought(dto);
        return ResponseEntity.ok(result);


    }*/


    /*@PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestBody ValidatorInsight insight) {
        System.out.println("Field: " + insight.getField());
        System.out.println("Message: " + insight.getMessage());
        System.out.println("Cue: " + insight.getEmotionalCue());
        return ResponseEntity.ok("Validated: " + insight.getField());


 }*/
}