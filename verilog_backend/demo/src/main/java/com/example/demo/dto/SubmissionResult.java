package com.example.demo.dto;

import com.example.demo.Model.Result;


public class SubmissionResult {
    private Result result;   // e.g., "Accepted", "Compilation Error"
    private String error;    // Error message if any
    private String output;   // Output from running the code

    public SubmissionResult(){

    }
    public SubmissionResult(Result result, String error, String output) {
        this.result = result;
        this.error = error;
        this.output = output;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    
}
