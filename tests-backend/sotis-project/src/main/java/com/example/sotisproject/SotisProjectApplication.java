package com.example.sotisproject;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringBootApplication
public class SotisProjectApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(SotisProjectApplication.class, args);

//		String omnibusPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\omnibus.owl" ;
//		String lomPath = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\LOM.owl" ;
//		String omnibusTTL = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\omnibus.ttl" ;
//		String lomPathTTL = "C:\\Users\\Srdjan Topic\\Desktop\\SOTIS\\SOTIS-project\\tests-backend\\sotis-project\\src\\main\\java\\com\\example\\sotisproject\\jena\\lom.ttl" ;
//
//		Model omnibusModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
//		omnibusModel.read(omnibusPath);
//		OutputStream omnibusOut = new FileOutputStream(omnibusTTL);
//		omnibusModel.write(omnibusOut, "TURTLE");
//
//		Model lomModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
//		lomModel.read(lomPath);
//		OutputStream lomOut = new FileOutputStream(lomPathTTL);
//		lomModel.write(lomOut, "TURTLE");

		

	}

}
