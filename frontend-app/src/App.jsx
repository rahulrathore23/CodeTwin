import { useState } from "react";
import "./App.css";

function App() {
  const [code, setCode] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const analyzeCode = async () => {
    if (!code.trim()) {
      alert("Please enter some code first!");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(
        "https://codetwin-1.onrender.com/api/code/analyze",
        {
          method: "POST",
          headers: {
            "Content-Type": "text/plain",
          },
          body: code,
        }
      );

      const data = await response.json();
      setResult(data);
    } catch (error) {
      alert("Backend is not connected. Make sure Spring Boot is running.");
    }

    setLoading(false);
  };

  return (
    <div className="app">
      <header>
        <h1>🚀 CodeTwin</h1>
        <p>AI that understands how you code</p>
      </header>

      <main>
        <section className="editor-section">
          <div className="section-title">
            <h2>Your Code</h2>
            <span>C++ / Java / Python</span>
          </div>

          <textarea
            value={code}
            onChange={(e) => setCode(e.target.value)}
            placeholder="// Paste your code here..."
          />

          <button onClick={analyzeCode} disabled={loading}>
            {loading ? "Analyzing..." : "Analyze Code →"}
          </button>
        </section>

        {result && (
          <section className="result-section">
            <h2>Code Analysis</h2>

            <div className="cards">
              <div className="card">
                <span>Language</span>
                <strong>{result.language}</strong>
              </div>

              <div className="card">
                <span>Pattern</span>
                <strong>{result.pattern}</strong>
              </div>

              <div className="card">
                <span>Time Complexity</span>
                <strong>{result.timeComplexity}</strong>
              </div>

              <div className="card">
                <span>Code Length</span>
                <strong>{result.codeLength}</strong>
              </div>
            </div>
          </section>
        )}
      </main>
    </div>
  );
}

export default App;