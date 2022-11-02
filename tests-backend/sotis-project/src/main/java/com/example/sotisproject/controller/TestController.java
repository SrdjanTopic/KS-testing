package com.example.sotisproject.controller;

import com.example.sotisproject.dto.TestDTO;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    private TestService testService;

    @PostMapping("/add")
    public Test addTest(@RequestBody Test test){
        return testService.addTest(test);
    }
}
