package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Problem;
import com.example.demo.Repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/problems")
public class ProblemController {

    @Autowired
    private ProblemRepository problemRepo;

   
    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems(){
        List<Problem> p=problemRepo.findAll();

        if(p.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(p);
    
    }
    @PostMapping
    public ResponseEntity<Problem> addProblem(@RequestBody Problem newProblem) {
        Problem savedProblem = problemRepo.save(newProblem);
        return ResponseEntity.ok(savedProblem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Integer id) {
        Optional<Problem> problem = problemRepo.findById(id);
        return problem.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
