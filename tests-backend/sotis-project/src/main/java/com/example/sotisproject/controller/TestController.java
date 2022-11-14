package com.example.sotisproject.controller;

import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    private TestService testService;

    @GetMapping("/")
    public List<Test> getTests(){
        return testService.getTests();
    }

    @PostMapping("/add")
    public Test addTest(@RequestBody Test test){
        return testService.addTest(test);
    }
}
