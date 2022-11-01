package com.example.sotisproject.controller;

import com.example.sotisproject.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/answer", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnswerController {
    private AnswerService answerService;
}
