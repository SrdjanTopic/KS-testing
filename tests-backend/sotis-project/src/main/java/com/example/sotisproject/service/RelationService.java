package com.example.sotisproject.service;

import com.example.sotisproject.jena.model.SotisOntologyModel;
import com.example.sotisproject.jena.service.OntologyService;
import com.example.sotisproject.model.Relation;
import com.example.sotisproject.repository.ConceptRepository;
import com.example.sotisproject.repository.RelationRepository;
import lombok.AllArgsConstructor;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RelationService {
    private RelationRepository relationRepository;
    private OntologyService ontologyService;
    private ConceptRepository conceptRepository;

    public List<Relation> getRelations() {
        return relationRepository.findAll();
    }

    public List<Relation> updateRelations(List<Relation> relations) {
        List<Relation> relationList = new ArrayList<>();
        //ontologyService.deleteRelations();
        relationRepository.deleteAll();
        relations.forEach(relation -> {
            relationList.add(relationRepository.save(relation));
            System.out.println(relation.getSource().getConcept());
        });

        //ontologyService.addRelations(relations);

//        try {
//            String stuTestPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\stuTest.owl";
//
//            OntModel stuTestModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
//            stuTestModel.read(stuTestPath);
//            OutputStream stuTestOut = new FileOutputStream(stuTestPath);
//            SotisOntologyModel sotisOntologyModel = new SotisOntologyModel(stuTestModel);
//            String queryString = "" +
//                    "PREFIX ns: <http://www.example.org/ontology/sotis#> \n" +
//                    "SELECT ?conceptName\n" +
//                    "WHERE {ns:CSS ns:isDestinationFor ?conceptName}";
//            Query query = QueryFactory.create(queryString);
//            try (QueryExecution qexec = QueryExecutionFactory.create(query, stuTestModel)) {
//                ResultSet results = qexec.execSelect();
//                ResultSetFormatter.out(System.out, results, query);
////                for (; results.hasNext(); ) {
////                    QuerySolution soln = results.nextSolution();
////                    //System.out.println(soln.toString());
////                    Literal s = soln.getLiteral("questionQuestion");
////                    System.out.println(s);
////                }
//            }
//            stuTestModel.write(stuTestOut, "RDF/XML");
//            stuTestOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return relationList;
    }

    public List<Long> getConceptOrder(){
        List<Long> sources = new ArrayList<>();
        List<Long> destinations = new ArrayList<>();
        List<Long> conceptOrder = new ArrayList<>();
        List<Long> currentConcepts = new ArrayList<>();
        List<Long> tempCurr = new ArrayList<>();
        List<Relation> relationList = relationRepository.findAll();
        relationList.forEach(relation -> {
            sources.add(relation.getSource().getId());
            destinations.add(relation.getDestination().getId());
        });
        sources.forEach(source->{if(!destinations.contains(source)&&!conceptOrder.contains(source)&&!currentConcepts.contains(source)) {
            conceptOrder.add(source);
            currentConcepts.add(source);
        }});
        sources.removeAll(currentConcepts);
        do{
            relationList.forEach(relation -> {
                if (currentConcepts.contains(relation.getSource().getId())&&!conceptOrder.contains(relation.getDestination().getId())) {
                    conceptOrder.add(relation.getDestination().getId());
                    tempCurr.add(relation.getDestination().getId());
                }
            });
            currentConcepts.clear();
            currentConcepts.addAll(tempCurr);
            sources.removeAll(currentConcepts);
        }while(sources.size()!=0);
        relationList.forEach(relation -> {
            if (currentConcepts.contains(relation.getSource().getId())&&!conceptOrder.contains(relation.getDestination().getId())) {
                conceptOrder.add(relation.getDestination().getId());
            }
        });
        return conceptOrder;
    }
}
