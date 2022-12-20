package com.example.sotisproject.service;

import com.example.sotisproject.jena.service.OntologyService;
import java.io.File;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.repository.QuestionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private AnswerService answerService;
    private OntologyService ontologyService;

    public Question addQuestion(Question question){
        Question newQuestion = questionRepository.save(new Question(null, question.getQuestion(), question.getPoints(), question.getTest(),null, question.getConcept()));
        ontologyService.addQuestion(question);
        question.getAnswers().forEach((Answer answer)->{
            answer.setQuestion(newQuestion);
            answerService.addAnswer(answer);
        });
        return newQuestion;
    }
    
    public FileSystemResource qti(Question question) {
    	 question = questionRepository.findById(question.getId()).get();
         Answer correctAnswer = new Answer();
         String filePath = new File("").getAbsolutePath() + "\\..\\..\\qti\\question.xml";
         if(question!= null)
         {
             Set<Answer> answers = question.getAnswers();
             for(Answer a : answers)
             {
                 if(a.getIsCorrect())
                 {
                     correctAnswer = a;
                 }
             }
             try{
                 DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                 DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                 Document document = documentBuilder.newDocument();

                 Element root = document.createElement("qti-assessment-item");

                 Attr attr = document.createAttribute("xmlns");
                 attr.setValue("http://www.imsglobal.org/xsd/qti/imsqtiasi_v3p0");
                 root.setAttributeNode(attr);

                 Attr attr1 = document.createAttribute("xmlns:xsi");
                 attr1.setValue("http://www.w3.org/2001/XMLSchema-instance");
                 root.setAttributeNode(attr1);

                 Attr attr2 = document.createAttribute("xsi:schemaLocation");
                 attr2.setValue("http://www.imsglobal.org/xsd/imsqtiasi_v3p0 https://purl.imsglobal.org/spec/qti/v3p0/schema/xsd/imsqti_asiv3p0_v1p0.xsd");
                 root.setAttributeNode(attr2);

                 Attr attr3 = document.createAttribute("identifier");
                 attr3.setValue("firstexample");
                 root.setAttributeNode(attr3);

                 Attr attr4 = document.createAttribute("time-dependent");
                 attr4.setValue("false");
                 root.setAttributeNode(attr4);

                 Attr attr5 = document.createAttribute("xml:lang");
                 attr5.setValue("en-US");
                 root.setAttributeNode(attr5);

                 document.appendChild(root);

                 Element qtiDeclaration = document.createElement("qti-response-declaration");

                 Attr attr6 = document.createAttribute("base-type");
                 attr6.setValue("identifier");
                 qtiDeclaration.setAttributeNode(attr6);

                 Attr attr7 = document.createAttribute("cardinality");
                 attr7.setValue("single");
                 qtiDeclaration.setAttributeNode(attr7);

                 Attr attr8 = document.createAttribute("identifier");
                 attr8.setValue("RESPONSE");
                 qtiDeclaration.setAttributeNode(attr8);

                 root.appendChild(qtiDeclaration);

                 Element qtiResponse = document.createElement("qti-correct-response");

                 qtiDeclaration.appendChild(qtiResponse);

                 Element qtiValue = document.createElement("qti-value");
                 qtiValue.appendChild(document.createTextNode(correctAnswer.getId().toString()));

                 qtiResponse.appendChild(qtiValue);

                 Element qtiOutcome = document.createElement("qti-outcome-declaration");

                 Attr attr9 = document.createAttribute("base-type");
                 attr9.setValue("integer");
                 qtiOutcome.setAttributeNode(attr9);

                 Attr attr10 = document.createAttribute("cardinality");
                 attr10.setValue("single");
                 qtiOutcome.setAttributeNode(attr10);

                 Attr attr11 = document.createAttribute("identifier");
                 attr11.setValue("SCORE");
                 qtiOutcome.setAttributeNode(attr11);

                 root.appendChild(qtiOutcome);

                 Element qtiDefault = document.createElement("qti-default-value");
                 qtiOutcome.appendChild(qtiDefault);

                 Element qtiValue1 = document.createElement("qti-value");
                 qtiValue1.appendChild(document.createTextNode(correctAnswer.getQuestion().getPoints().toString()));

                 qtiDefault.appendChild(qtiValue1);

                 Element body = document.createElement("qti-item-body");

                 root.appendChild(body);

                 Element p = document.createElement("p");
                 p.appendChild(document.createTextNode(question.getQuestion()));

                 body.appendChild(p);

                 Element qtiChoices = document.createElement("qti-choice-interaction");

                 Attr attr12 = document.createAttribute("max-choices");
                 attr12.setValue("1");
                 qtiChoices.setAttributeNode(attr12);

                 Attr attr13 = document.createAttribute("min-choices");
                 attr13.setValue("1");
                 qtiChoices.setAttributeNode(attr13);

                 Attr attr14 = document.createAttribute("response-identifier");
                 attr14.setValue("RESPONSE");
                 qtiChoices.setAttributeNode(attr14);

                 body.appendChild(qtiChoices);

                 for(Answer a : answers)
                 {
                     Element qtiChoice = document.createElement("qti-simple-choice");

                     Attr attr15 = document.createAttribute("identifier");
                     attr15.setValue(a.getId().toString());
                     qtiChoice.setAttributeNode(attr15);

                     qtiChoice.appendChild(document.createTextNode(a.getAnswer()));

                     qtiChoices.appendChild(qtiChoice);
                 }

                 Element qtiProcessing = document.createElement("qti-response-processing");

                 Attr attr16 = document.createAttribute("template");
                 attr16.setValue("https://purl.imsglobal.org/spec/qti/v3p0/rptemplates/match_correct");
                 qtiProcessing.setAttributeNode(attr16);

                 body.appendChild(qtiProcessing);

                 TransformerFactory transformerFactory = TransformerFactory.newInstance();
                 Transformer transformer = transformerFactory.newTransformer();
                 transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                 DOMSource domSource = new DOMSource(document);

                 StreamResult streamResult = new StreamResult(new File(filePath));

                 transformer.transform(domSource, streamResult);

             }catch (ParserConfigurationException pce) {
                 pce.printStackTrace();
             } catch (TransformerException tfe) {
                 tfe.printStackTrace();
             }
         }
         return new FileSystemResource(filePath);
    }
}