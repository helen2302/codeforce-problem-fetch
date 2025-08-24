package com.EzyCoding.problem_fetcher.service;

import com.EzyCoding.problem_fetcher.dto.ProblemDto;
import com.EzyCoding.problem_fetcher.dto.ProblemResponse;
import com.EzyCoding.problem_fetcher.dto.Submission;
import com.EzyCoding.problem_fetcher.dto.SubmissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ProblemService {
    private static final String PROBLEM_API_URL = "https://codeforces.com/api/problemset.problems";
    private static final String SUBMISSION_API_URL = "https://codeforces.com/api/user.status?handle=%s&from=1&count=1000";
    @Autowired
    private RestTemplate restTemplate;

    public ProblemResponse getProblems() {
        return restTemplate.getForObject(PROBLEM_API_URL, ProblemResponse.class);
    }

    public ProblemDto getProblem(int contestId, String index) {
        ProblemResponse response = getProblems();
        if (response != null && "OK".equals(response.getStatus())) {
            return response.getResult().getProblems().stream()
                    .filter(p -> p.getContestId() == contestId
                            && index.equals(p.getIndex()))
                    .findFirst() .orElse(null); }
        return null;
    }

    public String getVerdictForProblem(String handle, int contestId, String index) {
        String url = String.format(SUBMISSION_API_URL, handle);
        SubmissionResponse response = restTemplate.getForObject(url, SubmissionResponse.class);

        // Check for null or failed API response
        if (response == null || !"OK".equals(response.getStatus())) {
            throw new RuntimeException("Failed to fetch submissions from Codeforces API");
        }

        if (response.getResult() == null || response.getResult().isEmpty()) {
            return "NO_SUBMISSION";
        }

        // Filter submissions for the specific problem
        List<Submission> submissions = response.getResult().stream()
                .filter(s -> s.getProblem() != null
                        && s.getProblem().getContestId() != null
                        && s.getProblem().getContestId() == contestId
                        && index.equals(s.getProblem().getIndex()))
                .toList();

        if (submissions.isEmpty()) {
            return "NO_SUBMISSION";
        }

        // Check if any submission is OK
        boolean okFound = submissions.stream()
                .anyMatch(s -> "OK".equals(s.getVerdict()));

        return okFound ? "OK" : "FAILED";
    }

}
