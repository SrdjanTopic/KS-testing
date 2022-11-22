package com.example.sotisproject.controller;

import com.example.sotisproject.model.Test;
import com.example.sotisproject.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import java.util.List;
import org.springframework.web.bind.annotation.*;


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

//    @GetMapping()
//    public ResponseEntity<Set<Test>> getTests() {
//        return new ResponseEntity<Set<Test>>(testService.getTests(), HttpStatus.OK);
//    }
}
