package com.example.demo.dto;

public class SubmissionRequest {
    private Integer userId;
    private Integer problemId;
    private String code;

    
    public SubmissionRequest() {}

    
    public SubmissionRequest(Integer userId, Integer problemId, String code) {
        this.userId = userId;
        this.problemId = problemId;
        this.code = code;
    }

    
    public Integer getUserId() {
        return userId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public String getCode() {
        return code;
    }

    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
