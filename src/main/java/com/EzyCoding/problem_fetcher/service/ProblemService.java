package com.EzyCoding.problem_fetcher.service;

import com.EzyCoding.problem_fetcher.dto.ProblemDto;
import com.EzyCoding.problem_fetcher.dto.ProblemResponse;
import com.EzyCoding.problem_fetcher.dto.Submission;
import com.EzyCoding.problem_fetcher.dto.SubmissionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProblemService {
    @Value("${api.problem.fetch.url}")
    String problemApiUrl;
    @Value("${api.submission.fetch.url}")
    String submissionApiUrl;
    @Autowired
    private RestTemplate restTemplate;

    public ProblemResponse getProblems() {
        return restTemplate.getForObject(problemApiUrl, ProblemResponse.class);
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

    public List<Submission> getAllSubmissions (String handle) {
        int from = 1;
        int batchSize = 1000;
        List <Submission> allSubmissions = new ArrayList<>();
        List <Submission> okSubmissions = new ArrayList<>();
        while (true) {
            String url = String.format(submissionApiUrl, handle, from, batchSize);
            SubmissionResponse response = restTemplate.getForObject(url, SubmissionResponse.class);
            // Check for null or failed API response
            if (response == null || !"OK".equals(response.getStatus())) {
                throw new RuntimeException("Failed to fetch submissions from Codeforces API");
            }
            List<Submission> batch = response.getResult();
            if (batch == null || batch.isEmpty()) {
                if (allSubmissions.isEmpty()) {
                    log.info("No submissions found for handle: " + handle);
                } else {
                    log.info("Finished fetching all submissions. Total: " + allSubmissions.size());
                }
                break; // stop fetching
            }
            allSubmissions.addAll(batch);
            // Move to the next page
            from += batchSize;
        }
        allSubmissions.removeIf(s -> !"OK".equals(s.getVerdict()));
        return allSubmissions;
    }
}
