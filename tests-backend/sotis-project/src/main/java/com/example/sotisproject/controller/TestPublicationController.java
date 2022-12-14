package com.example.sotisproject.controller;

import com.example.sotisproject.dto.NewTestPublicationDTO;
import com.example.sotisproject.model.TestPublication;
import com.example.sotisproject.service.TestPublicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/testPublications", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestPublicationController {
    private TestPublicationService testPublicationService;

    @GetMapping("/{testId}")
    public List<TestPublication> getTest(@PathVariable("testId") Long testId){
        return testPublicationService.findAllByTestId(testId);
    }
    @PostMapping("/publishNewVersion")
    public TestPublication publishNewVersionOfTest(@RequestBody NewTestPublicationDTO newTestPublicationDTO){
        return testPublicationService.publishNewVersionOfTest(newTestPublicationDTO);
    }
    @PostMapping("/{publicationId}/republish")
    public TestPublication rePublishOldVersionOfTest(@PathVariable("publicationId")Long publicationId){
        return testPublicationService.rePublishOlderVersionOfTest(publicationId);
    }
}
