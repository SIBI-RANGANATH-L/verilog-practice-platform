package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Model.Submission;
import com.example.demo.Repository.SubmissionRepository;
import com.example.demo.Service.SubmissionService;
import com.example.demo.dto.*;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @PostMapping("/submit")
    public ResponseEntity<SubmissionResult> submitCode(@RequestBody SubmissionRequest request) {

        SubmissionResult result = submissionService.processSubmission(request);

        Submission newSubmissions = new Submission();
        newSubmissions.setUserId(request.getUserId());
        newSubmissions.setProblemId(request.getProblemId());
        newSubmissions.setLanguage("verilog");
        newSubmissions.setCode(request.getCode());
        newSubmissions.setResult(result.getResult());
        newSubmissions.setRuntimeMs(10);
        newSubmissions.setMemoryKb(245);
        // newSubmission.

        submissionRepository.save(newSubmissions);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/run")
    public ResponseEntity<SubmissionResult> postMethodName(@RequestBody SubmissionRequest request) {
        
    
     

        SubmissionResult result = submissionService.processSubmission(request);

        Submission newSubmissions = new Submission();
        newSubmissions.setUserId(request.getUserId());
        newSubmissions.setProblemId(request.getProblemId());
        newSubmissions.setLanguage("verilog");
        newSubmissions.setCode(request.getCode());
        newSubmissions.setResult(result.getResult());
        newSubmissions.setRuntimeMs(10);
        newSubmissions.setMemoryKb(245);
        
        return ResponseEntity.ok(result);
    }
}
