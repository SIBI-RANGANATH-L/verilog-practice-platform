package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    
}
