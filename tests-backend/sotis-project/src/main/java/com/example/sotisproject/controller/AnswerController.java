package com.example.sotisproject.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sotisproject.service.AnswerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/answers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnswerController {
    private AnswerService answerService;
}
