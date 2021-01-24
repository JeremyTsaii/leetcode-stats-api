package leetcode.api;

import org.springframework.stereotype.Service;

@Service
public interface StatsService {
    StatsResponse getStats(String username);
}
