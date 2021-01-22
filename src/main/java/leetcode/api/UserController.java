package leetcode.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
public class UserController {

    @GetMapping(value ={"/{username}", "/"})
    public String getStats(@PathVariable Optional<String> username) {
        if (username.isPresent()) {
            return String.format("Your username is %s!", username);
        } else {
            return "Please enter your username (ex: app.com/LeetCodeUsername)";
        }
    }
}

