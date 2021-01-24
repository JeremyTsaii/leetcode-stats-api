package leetcode.api.service;

import leetcode.api.model.StatsResponse;

public interface StatsService {
    StatsResponse getStats(String username);
}
