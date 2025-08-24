package com.EzyCoding.problem_fetcher.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProblemResult {
    private List<ProblemDto> problems;
}