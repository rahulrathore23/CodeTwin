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
    setResult(null);

    try {

      const response = await fetch(
        "https://codetwin-1.onrender.com/api/code/analyze",
        {
          method: "POST",

          headers: {
            "Content-Type": "text/plain"
          },

          body: code
        }
      );

      if (!response.ok) {

        throw new Error(
          `Server returned ${response.status}`
        );

      }

      const data = await response.json();

      setResult(data);

    } catch (error) {

      console.error("Backend Error:", error);

      alert(
        "Backend is not connected. Please try again."
      );

    } finally {

      setLoading(false);

    }
  };

  return (

    <div className="app">

      {/* HEADER */}

      <header className="header">

        <h1>
          🚀 CodeTwin
        </h1>

        <p>
          AI that understands how you code
        </p>

      </header>


      {/* MAIN */}

      <main>

        {/* CODE EDITOR */}

        <section className="editor-section">

          <div className="section-title">

            <h2>
              Your Code
            </h2>

            <span>
              C++ / Java / Python
            </span>

          </div>


          <textarea
            value={code}
            onChange={(e) => setCode(e.target.value)}
            placeholder="// Paste your C++, Java or Python code here..."
          />


          <button
            onClick={analyzeCode}
            disabled={loading}
          >

            {loading
              ? "Analyzing..."
              : "Analyze Code →"}

          </button>

        </section>


        {/* RESULT */}

        {result && (

          <section className="result-section">

            <h2>
              Code Analysis
            </h2>


            <div className="cards">


              {/* LANGUAGE */}

              <div className="card">

                <span>
                  Language
                </span>

                <strong>
                  {result.language}
                </strong>

              </div>


              {/* PATTERN */}

              <div className="card">

                <span>
                  Pattern
                </span>

                <strong>
                  {result.pattern}
                </strong>

              </div>


              {/* COMPLEXITY */}

              <div className="card">

                <span>
                  Time Complexity
                </span>

                <strong>
                  {result.timeComplexity}
                </strong>

              </div>


              {/* CODE LENGTH */}

              <div className="card">

                <span>
                  Code Length
                </span>

                <strong>
                  {result.codeLength}
                </strong>

              </div>


              {/* AI LIKELIHOOD */}

              <div className="card">

                <span>
                  AI Likelihood
                </span>

                <strong>
                  {result.aiLikelihood}%
                </strong>

              </div>


              {/* CONFIDENCE */}

              <div className="card">

                <span>
                  Confidence
                </span>

                <strong>
                  {result.confidence}
                </strong>

              </div>

            </div>

          </section>

        )}

      </main>


      {/* FOOTER */}

      <footer>

        <p>
          CodeTwin © 2026
        </p>

      </footer>

    </div>

  );
}

export default App;