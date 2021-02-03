package leetcode.api;

import leetcode.api.model.StatsResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class StatsResponseTests {
    final Map<String, Integer> submissionCalendar = new HashMap<>();

    {
        submissionCalendar.put("1610755200", 2);
    }

    StatsResponse s = new StatsResponse("success", "retrieved", 1, 2, 3, 4, 5, 6, 7, 8, (float) 99.99, 10, 11, 12, submissionCalendar);

    @Test
    void statusCorrect() {
        assertEquals("success", s.getStatus());
    }

    @Test
    void messageCorrect() {
        assertEquals("retrieved", s.getMessage());
    }

    @Test
    void totalSolvedCorrect() {
        assertEquals(1, s.getTotalSolved());
    }

    @Test
    void totalQuestionsCorrect() {
        assertEquals(2, s.getTotalQuestions());
    }

    @Test
    void easySolvedCorrect() {
        assertEquals(3, s.getEasySolved());
    }

    @Test
    void totalEasyCorrect() {
        assertEquals(4, s.getTotalEasy());
    }

    @Test
    void mediumSolvedCorrect() {
        assertEquals(5, s.getMediumSolved());
    }

    @Test
    void totalMediumCorrect() {
        assertEquals(6, s.getTotalMedium());
    }

    @Test
    void hardSolvedCorrect() {
        assertEquals(7, s.getHardSolved());
    }

    @Test
    void totalHardCorrect() {
        assertEquals(8, s.getTotalHard());
    }

    @Test
    void acceptanceCorrect() {
        assertEquals((float) 99.99, s.getAcceptanceRate());
    }

    @Test
    void rankingCorrect() {
        assertEquals(10, s.getRanking());
    }

    @Test
    void contributionPointsCorrect() {
        assertEquals(11, s.getContributionPoints());
    }

    @Test
    void reputationCorrect() {
        assertEquals(12, s.getReputation());
    }

    @Test
    void sameRefEqualCorrect() {
        assertEquals(s, s);
    }

    @Test
    void submissionCalendarCorrect() {
        assertEquals(submissionCalendar, s.getSubmissionCalendar());
    }

    @Test
    void sameValEqualCorrect() {
        StatsResponse copy = new StatsResponse("success", "retrieved", 1, 2, 3, 4, 5, 6, 7, 8, (float) 99.99, 10, 11, 12, submissionCalendar);
        assertTrue(s.equals(copy));
    }
}
