package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://code-twin-coral.vercel.app",
        "https://code-twin-git-main-rahulrathore23.vercel.app"
})
public class CodeAnalysisController {

    @PostMapping("/analyze")
    public String analyzeCode(@RequestBody String code) {

        int codeLength = code.length();

        String language = detectLanguage(code);
        String pattern = detectPattern(code);
        String timeComplexity = detectComplexity(code);

        return """
                {
                    "codeLength": %d,
                    "timeComplexity": "%s",
                    "pattern": "%s",
                    "language": "%s"
                }
                """.formatted(
                codeLength,
                timeComplexity,
                pattern,
                language
        );
    }

    private String detectLanguage(String code) {

        if (code.contains("def ") ||
            code.contains("import ") ||
            code.contains("print(") ||
            code.contains("elif ") ||
            code.contains("None")) {

            return "Python";
        }

        if (code.contains("#include") ||
            code.contains("using namespace std") ||
            code.contains("vector<") ||
            code.contains("unordered_map<")) {

            return "C++";
        }

        if (code.contains("public static void main") ||
            code.contains("System.out.println") ||
            code.contains("class ") && code.contains("public static")) {

            return "Java";
        }

        return "Unknown";
    }

    private String detectPattern(String code) {

        if (code.contains("unordered_map") ||
            code.contains("HashMap") ||
            code.contains("dict") ||
            code.contains(" in mp")) {

            return "Hash Map";
        }

        if (code.contains("left") && code.contains("right")) {
            return "Two Pointer";
        }

        if (code.contains("while")) {
            return "Iteration";
        }

        if (code.contains("for")) {
            return "Loop";
        }

        return "Basic";
    }

    private String detectComplexity(String code) {

        if (code.contains("for") || code.contains("while")) {
            return "O(n)";
        }

        return "O(1)";
    }
}