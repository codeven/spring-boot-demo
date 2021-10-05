package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalTime;

@Controller
public class HomeController {

    @RequestMapping("/")
    public @ResponseBody String home() {
        return "Welcome to the home page!";
    }

    @RequestMapping("/action")
    public @ResponseBody String action() throws Exception {
        if (LocalTime.now().getSecond() % 2 == 0) {
            throw new Exception("an error occurs while processing");
        } else {
            return "Running an action...";
        }
    }

}
