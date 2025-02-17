package main.java.org.example.controller;

import org.example.model.Verdict;
import org.example.model.VerdictRequest;
import org.example.service.VerdictService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/verdicts")
public class VerdictController {
    private final VerdictService verdictService;

    public VerdictController(VerdictService verdictService) {
        this.verdictService = verdictService;
    }

    @PostMapping
    public ResponseEntity<Verdict> addVerdict(@RequestBody VerdictRequest request)
    {
        Verdict verdict = verdictService.addVerdict(
                request.getAuthorId(),
                request.getTargetId(),
                request.getTargetType(),
                request.getText(),
                request.getRating(),
                request.isPublic()
        );
        return ResponseEntity.ok(verdict);
    }

    @GetMapping
    public ResponseEntity<List<Verdict>> getVerdicts(@RequestParam String targetId){
        return ResponseEntity.ok(verdictService.getVerdicts(targetId));
    }

    @PostMapping("/{verdictId}/like")
    public ResponseEntity<String> likeVerdict(@PathVariable String verdictId){
        boolean success = verdictService.likeVerdict(verdictId);
        return success ? ResponseEntity.ok("Verdict liked!") : ResponseEntity.badRequest().body("Verdict not found.");
    }

    @PostMapping("/{verdictId}/report")
    public ResponseEntity<String> reportVerdict(@PathVariable String verdictId){
        boolean success = verdictService.reportVerdict(verdictId);
        return success ? ResponseEntity.ok("Verdict reported!") : ResponseEntity.badRequest().body("Verdict not found.");
    }
}
