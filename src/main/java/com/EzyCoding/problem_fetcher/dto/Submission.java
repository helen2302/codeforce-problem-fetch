package com.EzyCoding.problem_fetcher.dto;

import lombok.Data;

@Data
public class Submission {
    private Integer id;
    private Integer contestId;
    private Long creationTimeSeconds;
    private Long relativeTimeSeconds;
    private ProblemDto problem;
    private Party author;
    private String programmingLanguage;
    private String verdict;
    private String testset;
    private Integer passedTestCount;
    private Integer timeConsumedMillis;
    private Long memoryConsumedBytes;
    private Double points;

}
