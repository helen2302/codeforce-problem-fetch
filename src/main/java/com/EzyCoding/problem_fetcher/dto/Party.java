package com.EzyCoding.problem_fetcher.dto;

import lombok.Data;

import java.util.List;

@Data
public class Party {
    private Integer contestId;
    private List<Member> members;
    private String participantType;
    private Integer teamId;
    private String teamName;
    private Boolean ghost;
    private Integer room;
    private Long startTimeSeconds;

    @Data
    public static class Member {
        private String handle;
    }
}