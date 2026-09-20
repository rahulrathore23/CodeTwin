package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = "*")
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