package com.EzyCoding.problem_fetcher.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProblemDto {
    private Integer contestId;
    private String index;
    private String name;
    private String type;
    private Integer rating;
    private List<String> tags;
}
