package exercise.controller;

import exercise.daytime.Day;
import exercise.daytime.Daytime;
import exercise.daytime.Night;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
@RequestMapping(path = "/welcome")
public class WelcomeController {

    @Autowired
    Daytime daytime;

    @GetMapping
    public String getWelcome() {
        if (daytime instanceof Night) {
            return "It is night now! Welcome to Spring!";
        } else return "It is day now! Welcome to Spring!";
    }

}
// END
