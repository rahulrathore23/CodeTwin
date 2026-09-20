package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = "*")
public class CodeAnalysisController {

    @PostMapping(
            value = "/analyze",
            consumes = "text/plain",
            produces = "application/json"
    )
    public String analyzeCode(@RequestBody String code) {

        int codeLength = code.length();

        String language = detectLanguage(code);
        String pattern = detectPattern(code);
        String timeComplexity = detectComplexity(code);

        int aiLikelihood = calculateAIScore(code);

        String confidence;

        if (aiLikelihood >= 70) {
            confidence = "Medium";
        } else {
            confidence = "Low";
        }

        return """
                {
                    "codeLength": %d,
                    "language": "%s",
                    "pattern": "%s",
                    "timeComplexity": "%s",
                    "aiLikelihood": %d,
                    "confidence": "%s"
                }
                """.formatted(
                codeLength,
                language,
                pattern,
                timeComplexity,
                aiLikelihood,
                confidence
        );
    }

    // ==========================================
    // LANGUAGE DETECTION
    // ==========================================

    private String detectLanguage(String code) {

        String lower = code.toLowerCase();

        // Python
        if (code.contains("def ")
                || code.contains("import ")
                || code.contains("print(")
                || code.contains("elif ")
                || code.contains("None")
                || code.contains("True")
                || code.contains("False")) {

            return "Python";
        }

        // C++
        if (code.contains("#include")
                || code.contains("using namespace std")
                || code.contains("vector<")
                || code.contains("unordered_map<")
                || code.contains("cout <<")
                || code.contains("cin >>")) {

            return "C++";
        }

        // Java
        if (code.contains("public static void main")
                || code.contains("System.out.println")
                || code.contains("import java.")
                || code.contains("public class ")) {

            return "Java";
        }

        return "Unknown";
    }

    // ==========================================
    // PATTERN DETECTION
    // ==========================================

    private String detectPattern(String code) {

        String lower = code.toLowerCase();

        if (lower.contains("unordered_map")
                || lower.contains("hashmap")
                || lower.contains("dict")
                || lower.contains("dictionary")) {

            return "Hash Map";
        }

        if ((lower.contains("left") && lower.contains("right"))
                || lower.contains("two pointer")) {

            return "Two Pointer";
        }

        if (lower.contains("sliding window")) {
            return "Sliding Window";
        }

        if (lower.contains("binary search")
                || lower.contains("mid =")
                || lower.contains("mid=")) {

            return "Binary Search";
        }

        if (lower.contains("dp[")
                || lower.contains("memo")
                || lower.contains("dynamic programming")) {

            return "Dynamic Programming";
        }

        if (lower.contains("sort(")
                || lower.contains(".sort()")) {

            return "Sorting";
        }

        if (lower.contains("for ")
                || lower.contains("for(")
                || lower.contains("while ")
                || lower.contains("while(")) {

            return "Iteration";
        }

        return "Basic";
    }

    // ==========================================
    // TIME COMPLEXITY
    // ==========================================

    private String detectComplexity(String code) {

        String lower = code.toLowerCase();

        int forCount =
                countOccurrences(lower, "for ")
                        + countOccurrences(lower, "for(");

        int whileCount =
                countOccurrences(lower, "while ")
                        + countOccurrences(lower, "while(");

        int totalLoops = forCount + whileCount;

        if (totalLoops >= 2) {
            return "O(n²)";
        }

        if (lower.contains("binary search")
                || lower.contains("mid =")
                || lower.contains("mid=")) {

            return "O(log n)";
        }

        if (totalLoops == 1) {
            return "O(n)";
        }

        return "O(1)";
    }

    // ==========================================
    // AI LIKELIHOOD
    // ==========================================

    private int calculateAIScore(String code) {

        int score = 0;

        String lower = code.toLowerCase();

        // Comments
        if (code.contains("//")
                || code.contains("/*")
                || code.contains("# ")) {

            score += 10;
        }

        // Explanatory words
        if (lower.contains("initialize")
                || lower.contains("calculate")
                || lower.contains("implementation")
                || lower.contains("approach")
                || lower.contains("solution")
                || lower.contains("returns")) {

            score += 15;
        }

        // Structured formatting
        if (code.contains("{")
                && code.contains("}")
                && code.contains("\n")) {

            score += 10;
        }

        // Common algorithm structures
        if (lower.contains("unordered_map")
                || lower.contains("hashmap")
                || lower.contains("two pointer")
                || lower.contains("sliding window")
                || lower.contains("binary search")) {

            score += 15;
        }

        // Programming structures
        if (lower.contains("def ")
                || lower.contains("public static")
                || lower.contains("vector<")
                || lower.contains("function")) {

            score += 10;
        }

        // Code length
        if (code.length() > 300) {
            score += 10;
        }

        if (code.length() > 600) {
            score += 10;
        }

        // Multiple functions
        int functionCount = 0;

        for (String line : code.split("\n")) {

            String trimmed = line.trim();

            if (trimmed.startsWith("def ")
                    || trimmed.contains("public ")
                    || trimmed.contains("private ")
                    || trimmed.contains("protected ")) {

                functionCount++;
            }
        }

        if (functionCount >= 2) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    // ==========================================
    // HELPER METHOD
    // ==========================================

    private int countOccurrences(String text, String word) {

        int count = 0;
        int index = 0;

        while ((index = text.indexOf(word, index)) != -1) {

            count++;

            index += word.length();
        }

        return count;
    }
}