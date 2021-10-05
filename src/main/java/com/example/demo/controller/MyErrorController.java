package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public @ResponseBody String handleError(HttpServletRequest request) {
        final Optional<Integer> statusCode = Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE))
                .map(Object::toString)
                .map(Integer::valueOf);

        if (statusCode.isPresent() && HttpStatus.NOT_FOUND.value() == statusCode.get()) {
            return "Sorry, we couldn't find the page you are looking for";
        } else {
            return "Something went wrong!";
        }
    }

}
