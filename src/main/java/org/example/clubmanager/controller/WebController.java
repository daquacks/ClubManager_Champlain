package org.example.clubmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/clubs")
    public String clubs() {
        return "index"; // For now, reuse index or create a separate clubs list page
    }

    @GetMapping("/clubs/{id}")
    public String clubDetails() {
        return "club";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Server is running!";
    }
}
