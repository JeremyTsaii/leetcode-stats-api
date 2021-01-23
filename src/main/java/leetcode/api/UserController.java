package leetcode.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    StatsService statsService;

    @GetMapping(value ={"/{username}", "/"})
    public StatsResponse getStats(@PathVariable Optional<String> username) {
        if (username.isPresent()) {
            return statsService.getStats(username.get());
        } else {
            String status = "error";
            String msg = "please enter your username (ex: leetcode-stats-api.herokuapp.com/LeetCodeUsername)";
            return new StatsResponse(status, msg, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
    }
}

