package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Submission;


@Repository
public interface  SubmissionRepository extends JpaRepository<Submission, Integer>{
    
}
