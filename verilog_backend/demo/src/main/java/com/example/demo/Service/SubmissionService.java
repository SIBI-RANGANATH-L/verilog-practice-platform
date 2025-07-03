package com.example.demo.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Problem;
import com.example.demo.Model.Result;
import com.example.demo.Repository.ProblemRepository;
import com.example.demo.dto.SubmissionRequest;
import com.example.demo.dto.SubmissionResult;

@Service
public class SubmissionService {

    @Autowired
    private ProblemRepository problemRepository;

   

    public SubmissionResult processSubmission(SubmissionRequest request) {
        Optional<Problem> optProblem = problemRepository.findById(request.getProblemId());

        if (optProblem.isEmpty()) {
            return new SubmissionResult(Result.Compilation_Error, null, null);
        }

        Problem problem = optProblem.get();
        String tb = problem.getTestbench();
        String expectedOutput = problem.getExpectedOutput();

        String userOutput = compilerVerilogCode(request.getCode(), tb);

        System.out.println("Expected Output \n:"+expectedOutput);
        System.out.println("User Output \n:"+userOutput);

        if (userOutput == null) {
            return new SubmissionResult(Result.Runtime_Error, null, null);
        }

        boolean isCorrect = userOutput.trim().equals(expectedOutput.trim());
        Result status = isCorrect ? Result.Accepted : Result.Wrong_Answer;

        return new SubmissionResult(status, expectedOutput, userOutput);
    }

    private String compilerVerilogCode(String code, String tb) {
        String id = UUID.randomUUID().toString();
        String codeFile = "/tmp/user_" + id + ".v";
        String tbFile = "/tmp/tb_" + id + ".v";
        String outFile = "/tmp/a.out";

        try {
            // Write user code
            BufferedWriter codeWriter = new BufferedWriter(new FileWriter(codeFile));
            codeWriter.write(code);
            codeWriter.close();

            // Write testbench
            BufferedWriter tbWriter = new BufferedWriter(new FileWriter(tbFile));
            tbWriter.write(tb);
            tbWriter.close();

            // Compile using iverilog
            Process compile = new ProcessBuilder("iverilog", "-o", outFile, codeFile, tbFile)
                    .redirectErrorStream(true)
                    .start();
            compile.waitFor();

            if (compile.exitValue() != 0) {
                return null;
            }

            // Run simulation with vvp
            Process run = new ProcessBuilder("vvp", outFile)
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
               if (!line.contains("$finish called")) {
                    output.append(line).append("\n");
                }
            }

            System.out.println(line);

            run.waitFor();

            return output.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Clean up files
            new File(codeFile).delete();
            new File(tbFile).delete();
            new File(outFile).delete();
        }
    }
}
