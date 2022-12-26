package com.example.sotisproject.jena.sparql;

import com.example.sotisproject.jena.model.SotisOntologyModel;
import lombok.AllArgsConstructor;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/sparql", produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController {
    private static final String stuTestPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\stuTest.owl";
    private static final String NS = "http://www.example.org/ontology/sotis#";

    @GetMapping("/{conceptName}/directNextConcepts")
    public List<String> findAllNextConceptsForConcept(@PathVariable("conceptName") String conceptName){
        List<String> returnConcepts = new ArrayList<>();
        try {
            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            stuTestModel.read(stuTestPath);
            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);
            String queryString = "" +
                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "SELECT ?conceptName\n" +
                    "WHERE {ns:"+ conceptName + " ns:isSourceFor ?uri . ?uri ns:conceptName ?conceptName}";
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
                ResultSet results = qexec.execSelect();
//                ResultSetFormatter.out(System.out, results, query);
                for (; results.hasNext(); ) {
                    QuerySolution soln = results.nextSolution();
                    //System.out.println(soln.toString());
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
}
