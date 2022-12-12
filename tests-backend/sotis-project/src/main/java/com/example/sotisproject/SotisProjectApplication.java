package com.example.sotisproject;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SotisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SotisProjectApplication.class, args);
		Model m = ModelFactory.createDefaultModel();
		Resource resource1 = m.createResource("http://rdfData/resource1");
		resource1.addProperty(VCARD.FN,  "Some kind of resource");

		m.write(System.out);
	}

}
