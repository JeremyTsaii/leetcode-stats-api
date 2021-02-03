package leetcode.api;

import leetcode.api.controller.UserController;
import leetcode.api.model.StatsResponse;
import leetcode.api.service.StatsService;
import leetcode.api.service.StatsServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService service;


    @Test
    void validUsername() throws Exception {
        final Map<String, Integer> submissionCalendar = new HashMap<>();
        submissionCalendar.put("1610755200", 2);
        StatsResponse mockResponse = new StatsResponse("success", "retrieved", 1, 2,3, 4, 5, 6, 7, 8, (float) 99.99, 10, 11, 12, submissionCalendar);
        when(service.getStats("user_exists")).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(get("/user_exists")).andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();
        String expected = "{\"status\":\"success\",\"message\":\"retrieved\",\"totalSolved\":1,\"totalQuestions\":2,\"easySolved\":3,\"totalEasy\":4,\"mediumSolved\":5,\"totalMedium\":6,\"hardSolved\":7,\"totalHard\":8,\"acceptanceRate\":99.99,\"ranking\":10,\"contributionPoints\":11,\"reputation\":12,\"submissionCalendar\":{\"1610755200\":2}}";
        assertEquals(resultStr, expected);
    }

    @Test
    void noUsername() throws Exception {
        MvcResult result = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();
        String expected = "{\"status\":\"error\",\"message\":\"please enter your username (ex: leetcode-stats-api.herokuapp.com/LeetCodeUsername)\",\"totalSolved\":0,\"totalQuestions\":0,\"easySolved\":0,\"totalEasy\":0,\"mediumSolved\":0,\"totalMedium\":0,\"hardSolved\":0,\"totalHard\":0,\"acceptanceRate\":0.0,\"ranking\":0,\"contributionPoints\":0,\"reputation\":0,\"submissionCalendar\":{}}";
        assertEquals(resultStr, expected);
    }

    @Test
    void nonValidUsername() throws Exception {
        StatsResponse mockResponse = StatsResponse.error("error", "user does not exist");
        when(service.getStats("user_does_not_exist")).thenReturn(mockResponse);

        MvcResult result = mockMvc.perform(get("/user_does_not_exist")).andExpect(status().isOk()).andReturn();
        String resultStr = result.getResponse().getContentAsString();
        String expected = "{\"status\":\"error\",\"message\":\"user does not exist\",\"totalSolved\":0,\"totalQuestions\":0,\"easySolved\":0,\"totalEasy\":0,\"mediumSolved\":0,\"totalMedium\":0,\"hardSolved\":0,\"totalHard\":0,\"acceptanceRate\":0.0,\"ranking\":0,\"contributionPoints\":0,\"reputation\":0,\"submissionCalendar\":{}}";
        assertEquals(resultStr, expected);
    }
}
