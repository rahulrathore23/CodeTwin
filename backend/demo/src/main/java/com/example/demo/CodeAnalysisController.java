package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://code-twin-git-main-rahulrathore23.vercel.app"
})
public class CodeAnalysisController {

    @PostMapping("/analyze")
    public String analyzeCode(@RequestBody String code) {

        int codeLength = code.length();

        return """
                {
                    "codeLength": %d,
                    "timeComplexity": "O(n)",
                    "pattern": "Two Pointer / Sliding Window",
                    "language": "C++"
                }
                """.formatted(codeLength);
    }
}