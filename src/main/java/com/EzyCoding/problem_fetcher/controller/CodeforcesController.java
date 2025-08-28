package com.EzyCoding.problem_fetcher.controller;

import com.EzyCoding.problem_fetcher.dto.ProblemDto;
import com.EzyCoding.problem_fetcher.dto.ProblemResponse;
import com.EzyCoding.problem_fetcher.dto.Submission;
import com.EzyCoding.problem_fetcher.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/codeforces")
public class CodeforcesController {

    @Autowired
    private ProblemService problemService;

         //Fetch a specific problem
    @GetMapping("/problem/{contestId}/{index}")
    public ProblemDto getProblem(@PathVariable int contestId,
                                 @PathVariable String index) {
        return problemService.getProblem(contestId, index);
    }

        // Fetch all problems
    @GetMapping("/problems")
    public ProblemResponse getProblems() {
        return problemService.getProblems();
    }

    @GetMapping("/verdict/{handle}")
    public List<Submission> getVerdictForProblem(@PathVariable String handle) {
        return problemService.getAllSubmissions(handle);
    }
}
