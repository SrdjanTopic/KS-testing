package com.example.sotisproject.service;

import com.example.sotisproject.dto.NewTestPublicationDTO;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.model.TestPublication;
import com.example.sotisproject.repository.RealKnowledgeSpaceRepository;
import com.example.sotisproject.repository.TestPublicationRepository;
import com.example.sotisproject.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TestPublicationService {
    private TestPublicationRepository testPublicationRepository;
    private TestRepository testRepository;
    private RealKnowledgeSpaceRepository realKnowledgeSpaceRepository;

    public TestPublication publishNewVersionOfTest(NewTestPublicationDTO newTestPublicationDTO){
        List<TestPublication> testPublicationList = testPublicationRepository.findAllByTestId(newTestPublicationDTO.getTestId());
        testPublicationList.forEach(testPublication -> {
            testPublication.setIsPublished(false);
            testPublicationRepository.save(testPublication);
        });

        Test test = testRepository.findById(newTestPublicationDTO.getTestId()).get();
        TestPublication testPublication = new TestPublication(null, true, LocalDateTime.now(), null, test);
        if(newTestPublicationDTO.getRksId() != null) testPublication.setRealKnowledgeSpace(realKnowledgeSpaceRepository.findById(newTestPublicationDTO.getRksId()).get());
        return testPublicationRepository.save(testPublication);
    }

    public TestPublication rePublishOlderVersionOfTest(Long publicationId){
        TestPublication testPublication = testPublicationRepository.findById(publicationId).get();
        testPublication.setIsPublished(true);

        List<TestPublication> testPublicationList = testPublicationRepository.findAllByTestId(testPublication.getTest().getId());
        testPublicationList.forEach(testPublicationn -> {
            testPublicationn.setIsPublished(false);
            testPublicationRepository.save(testPublicationn);
        });

        return testPublicationRepository.save(testPublication);
    }

    public List<TestPublication> findAllByTestId(Long testId) {
        return testPublicationRepository.findAllByTestId(testId);
    }
}
