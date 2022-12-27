package com.example.sotisproject.jena.sparql;

import com.example.sotisproject.jena.model.SotisOntologyModel;
import com.example.sotisproject.model.Concept;
import com.example.sotisproject.service.RelationService;
import lombok.AllArgsConstructor;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/sparql", produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController {
    private static final String stuTestPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\stuTest.owl";
    private static final String NS = "http://www.example.org/ontology/sotis#";

    private RelationService relationService;

    @GetMapping("/{conceptName}/directNextConcepts")
    public List<String> findAllDirectNextConceptsForConcept(@PathVariable("conceptName") String conceptName){

        List<String> returnConcepts = new ArrayList<>();
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            String queryString = "" +
                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
                    "SELECT DISTINCT ?conceptName\n" +
                    "WHERE {ns:"+ conceptName.replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_") + " ns:isSourceFor ?uri . ?uri ns:conceptName ?conceptName}";
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal s = soln.getLiteral("conceptName");
                    returnConcepts.add(s.toString());
                }
            }
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnConcepts;
    }

    @GetMapping("/{conceptName}/allPreviousConcepts")
    public List<String> findAllPreviousConceptsForConcept(@PathVariable("conceptName") String conceptName) {
        List<String> returnConcepts = new ArrayList<>();
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            String queryString = "" +
                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "SELECT DISTINCT ?conceptName \n" +
                    "WHERE { " +
                        "ns:"+ conceptName.replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")+" ns:isDestinationFor+ ?x . " +
                        "?x ns:conceptName ?conceptName . " +
//                        "?x ns:id ?id " +
//                        "bind( xsd:integer(?id) as ?conceptId )" +
                    " }";
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal name = soln.getLiteral("conceptName");
                    returnConcepts.add(name.getString());
                }
            }
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnConcepts;
    }

    @GetMapping("/{conceptName}/solvableTests")
    public List<String> findSolvableTestForLearnedConcept(@PathVariable("conceptName") String conceptName){
        List<String> returnTests = new ArrayList<>();
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            String queryString = "" +
                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
                    "SELECT * \n" +
                    "WHERE" +
                    "{" +
                        "{" +
                            "SELECT (GROUP_CONCAT(DISTINCT ?testName;SEPARATOR=\",\") AS ?badTestNames) \n" +
                            "WHERE" +
                            "{ " +
                                "{ " +
                                "SELECT (CONCAT(?argConceptName,\",\",GROUP_CONCAT(?conceptName;SEPARATOR=\",\")) AS ?allConceptNames)\n" +
                                "WHERE " +
                                "{ " +
                                    "?y ns:isDestinationFor+ ?x . " +
                                    "?y ns:conceptName ?argConceptName ." +
                                    "?x ns:conceptName ?conceptName ." +
                                    "FILTER(?argConceptName=\""+conceptName.replaceAll("[\"<>#%{}|^~\\\\\\]\\[ `]", "_")+"\" )" +
                                "}" +
                                "GROUP BY ?argConceptName" +
                                " }" +
                            "?test a ns:Test . " +
                            "?test ns:testName ?testName . " +
                            "?test ns:testQuestions ?question . " +
                            "?question ns:questionConcept ?concept . " +
                            "?concept ns:conceptName ?testConceptName ." +
                            "FILTER NOT EXISTS{FILTER CONTAINS(?allConceptNames , ?testConceptName)} ." +
                            "}" +
                            "GROUP BY ?allConceptNames" +
                        "}" +
                        "?tests ns:testName ?goodTestName . " +
                        "FILTER NOT EXISTS{FILTER CONTAINS(?badTestNames , ?goodTestName)} ." +
                    "}";
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal s = soln.getLiteral("goodTestName");
                    returnTests.add(s.getString());
                }
            }
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnTests;
    }

    @PostMapping("/studentsForTeam")
    public List<String> findStudentsCompetentForTeam(@RequestBody List<String> conceptNames) {
        List<String> returnStudentFullNames = new ArrayList<>();
        AtomicReference<String> filterString = new AtomicReference<>("");
        conceptNames.forEach(conceptName-> filterString.set(filterString + "FILTER CONTAINS(?learnedConcepts , \""+ conceptName +"\") ."));
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            String queryString = "" +
                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
                    "SELECT (CONCAT(?firstName, \" \", ?lastName) as ?fullName)" +
                    "WHERE" +
                    "{" +
                        "{" +
                        "SELECT ?student (GROUP_CONCAT(?conceptName;SEPARATOR=\",\")AS ?learnedConcepts) \n" +
                        "WHERE" +
                            "{" +
                            "?student ns:studentConcept ?learnedConcept . " +
                            "?learnedConcept ns:conceptName ?conceptName ." +
                            "}" +
                        "GROUP BY ?student" +
                        "}" +
                        "?student ns:userFirstName ?firstName ." +
                        "?student ns:userLastName ?lastName ." +
                        filterString.get() +
                    "}";
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal s = soln.getLiteral("fullName");
                    returnStudentFullNames.add(s.getString());
                }
            }
            stuTestModel.write(stuTestOut, "RDF/XML");
            stuTestOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStudentFullNames;
    }
}
