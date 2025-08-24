package com.EzyCoding.problem_fetcher.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionResponse {
    private String status;
    private List<Submission> result;
}
