package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "problems")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(columnDefinition = "json")
    private String tags;

    @Column(name = "code_stubs", columnDefinition = "json", nullable = false)
    private String codeStubs;

    @Column(name = "test_cases", columnDefinition = "json", nullable = false)
    private String testCases;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "testbench", columnDefinition = "TEXT", nullable = false)
    private String testbench;

    // Add this field after testbench
    @Column(name = "expected_output", columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
