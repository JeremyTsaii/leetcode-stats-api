package leetcode.api.controller;

import leetcode.api.model.StatsResponse;
import leetcode.api.service.StatsService;
import leetcode.api.service.StatsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private StatsService statsService;

    @GetMapping(value ={"/{username}", "/"})
    public StatsResponse getStats(@PathVariable Optional<String> username) {
        if (username.isPresent()) {
            StatsServiceImpl service = new StatsServiceImpl();
            return statsService.getStats(username.get());
        } else {
            String status = "error";
            String msg = "please enter your username (ex: leetcode-stats-api.herokuapp.com/LeetCodeUsername)";
            return StatsResponse.error(status, msg);
        }
    }
}

