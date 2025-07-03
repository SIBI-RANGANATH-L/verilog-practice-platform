import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Editor from '@monaco-editor/react';
import Split from 'react-split';
import './css/ProblemPage.css';

const ProblemPage = () => {
  const { id } = useParams();
  const [problem, setProblem] = useState(null);
  const [code, setCode] = useState('');
  const [submissionResult, setSubmissionResult] = useState(null);

  const sendCode = async (isRun) => {
    const URL = isRun
      ? 'http://localhost:8080/submission/run'
      : 'http://localhost:8080/submission/submit';

    try {
      const response = await fetch(URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: 1,
          problemId: Number(id),
          code: code,
        }),
      });

      const result = await response.json();
      console.log(result);

      setSubmissionResult({ ...result, type: isRun ? 'run' : 'submit' });
    } catch (error) {
      console.error('Error during submission:', error);
      setSubmissionResult({
        result: 'Submission Error',
        error: error.message,
        output: '',
        type: isRun ? 'run' : 'submit',
      });
    }
  };

  useEffect(() => {
    fetch(`http://localhost:8080/problems/${id}`)
      .then((res) => res.json())
      .then((data) => {
        try {
          if (typeof data.codeStubs === 'string') {
            data.codeStubs = JSON.parse(data.codeStubs);
          }
        } catch {
          data.codeStubs = {};
        }
        setProblem(data);
      });
  }, [id]);

  if (!problem) return <p>Loading...</p>;

  return (
    <Split className="split-container" sizes={[50, 50]} minSize={300} gutterSize={0.5}>
      <div className="left-pane">
        <h2>{problem.id}. {problem.title}</h2>
        <span className={`difficulty ${problem.difficulty.toLowerCase()}`}>
          {problem.difficulty}
        </span>
        <p className="description">{problem.description}</p>
        <h3>TESTBENCH</h3>
        <p style={{ whiteSpace: 'pre-wrap' }}>{problem.testbench}</p>
      </div>

      <div className="right-pane">
        <Editor
          height="60vh"
          language="verilog"
          value={code}
          defaultValue={problem.codeStubs?.verilog || '// Start coding here...'}
          theme="vs-dark"
          onChange={(value) => setCode(value)}
        />

        <div className="actions">
          <button onClick={() => sendCode(true)}>Run</button>
          <button onClick={() => sendCode(false)}>Submit</button>
        </div>

        {submissionResult && (
          <div className="submission-result">
            <h3>Result</h3>
            {submissionResult.type === 'submit' && (
              <p><strong>Status:</strong> {submissionResult.result}</p>
            )}
            {submissionResult.error && (
              <p><strong>Error:</strong> {submissionResult.error}</p>
            )}
            {submissionResult.output && (
              <p><strong>Output:</strong> {submissionResult.output}</p>
            )}
          </div>
        )}
      </div>
    </Split>
  );
};

export default ProblemPage;
