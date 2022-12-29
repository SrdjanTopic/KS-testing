package com.example.sotisproject.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.sotisproject.model.Answer;
import com.example.sotisproject.model.Question;
import com.example.sotisproject.model.Test;
import com.example.sotisproject.repository.AnswerRepository;
import com.example.sotisproject.repository.QuestionRepository;
import com.example.sotisproject.repository.StudentRepository;
import com.example.sotisproject.repository.TestRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class QTIService {
	@Autowired
	TestRepository testRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	TestService testService;

	public byte[] generateQTI(Long testId) {

		Test test = testRepository.findById(testId).get();

		// povratna vrednost funkcije je zip folder sa qti xml fajlovima
		File zip;
		if (!makeFolders(testId)) {
			System.out.println("Folder already exist");
		}
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element root = document.createElement("qti-assessment-test");

			Attr attr = document.createAttribute("xmlns");
			attr.setValue("http://www.imsglobal.org/xsd/qti/imsqtiasi_v3p0");
			root.setAttributeNode(attr);

			Attr attr1 = document.createAttribute("xmlns:xsi");
			attr1.setValue("http://www.w3.org/2001/XMLSchema-instance");
			root.setAttributeNode(attr1);

			Attr attr2 = document.createAttribute("xsi:schemaLocation");
			attr2.setValue(
					"http://www.imsglobal.org/xsd/imsqtiasi_v3p0 https://purl.imsglobal.org/spec/qti/v3p0/schema/xsd/imsqti_asiv3p0_v1p0.xsd");
			root.setAttributeNode(attr2);

			Attr attr3 = document.createAttribute("title");
			attr3.setValue(test.getName());
			root.setAttributeNode(attr3);

			Attr attr4 = document.createAttribute("identifier");
			attr4.setValue("TEST-" + test.getId());
			root.setAttributeNode(attr4);

			document.appendChild(root);

			Element qtiPart = document.createElement("qti-test-part");
			
			Attr attr5 = document.createAttribute("identifier");
			attr5.setValue("Part 1");
			qtiPart.setAttributeNode(attr5);

			Attr attr7 = document.createAttribute("navigation-mode");
			attr7.setValue("linear");
			qtiPart.setAttributeNode(attr7);

			Attr attr8 = document.createAttribute("submission-mode");
			attr8.setValue("individual");
			qtiPart.setAttributeNode(attr8);

			root.appendChild(qtiPart);

			Element qtiSection = document.createElement("qti-assessment-section");
			
			Attr attr6 = document.createAttribute("identifier");
			attr6.setValue("set");
			qtiSection.setAttributeNode(attr6);

			Attr sec = document.createAttribute("title");
			sec.setValue("Section 1");
			qtiSection.setAttributeNode(sec);

			Attr attr9 = document.createAttribute("visible");
			attr9.setValue("true");
			qtiSection.setAttributeNode(attr9);

			qtiPart.appendChild(qtiSection);

			Element qtiSelection = document.createElement("qti-selection");

			Attr attr10 = document.createAttribute("select");
			attr10.setValue("2");
			qtiSelection.setAttributeNode(attr10);

			int questionNumber = 1;
			for (Question q : test.getQuestions()) {

				generateQuestion(test, q, questionNumber);

				Element qtiItemRef = document.createElement("qti-assessment-item-ref");

				Attr attr11 = document.createAttribute("identifier");
				attr11.setValue("test" + test.getId() + "-" + questionNumber + ".xml");
				qtiItemRef.setAttributeNode(attr11);

				Attr attr12 = document.createAttribute("href");
				attr12.setValue(String.valueOf(questionNumber));
				qtiItemRef.setAttributeNode(attr12);

				qtiSection.appendChild(qtiItemRef);

				questionNumber++;
			}

			String filePathString = new File("").getAbsolutePath() + "\\QTI\\test-" + testId.toString() + "-QTI"
					+ "\\test-" + testId.toString() + "QTI.xml";
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);
			File file = new File(filePathString);
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StreamResult streamResult = new StreamResult(file);

			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File dir = new File("QTI/test-" + testId.toString() + "-QTI");
		zipDirectory(dir, "QTI/test-" + testId.toString() + "-QTI.zip");

		zip = new File("QTI/test-" + testId.toString() + "-QTI.zip");

		try {
			return convertFileContentToBlob(zip);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public FileSystemResource generateQuestion(Test test, Question question, int questionNumber) {
		Answer correctAnswer = new Answer();
		String filePath = new File("").getAbsolutePath() + "\\QTI\\test-" + test.getId().toString()
				+ "-QTI\\questions\\" + "test" + test.getId() + "-" + questionNumber + ".xml";
		if (question != null) {
			Set<Answer> answers = question.getAnswers();
			for (Answer a : answers) {
				if (a.getIsCorrect()) {
					correctAnswer = a;
				}
			}
			try {
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
				attr2.setValue(
						"http://www.imsglobal.org/xsd/imsqtiasi_v3p0 https://purl.imsglobal.org/spec/qti/v3p0/schema/xsd/imsqti_asiv3p0_v1p0.xsd");
				root.setAttributeNode(attr2);

				Attr attr3 = document.createAttribute("identifier");
				attr3.setValue("TEST-" + test.getId() + "-" + questionNumber);
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

				for (Answer a : answers) {
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
				File file = new File(filePath);
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StreamResult streamResult = new StreamResult(file);

				transformer.transform(domSource, streamResult);

			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
		return new FileSystemResource(filePath);
	}

	private Boolean generateQuestionXML(Test test, Question question, int questionNumber) {

		String questionXML = "<?xml version='1.0' encoding='UTF-8'?>\r\n";
		Answer correctAnswer = new Answer();
		for (Answer a : question.getAnswers()) {
			if (a.getIsCorrect()) {
				correctAnswer = a;
				break;
			}
		}
		questionXML += "<qti-assessment-item adaptive=\"false\" identifier=\"" + "TEST-" + test.getId() + "-"
				+ questionNumber + "\" time-dependent=\"false\" title=\"" + "Question " + questionNumber
				+ "\" xmlns=\"http://www.imsglobal.org/xsd/imsqtiasi_v3p0\" xmlns:xi=\"http://www.w3.org/2001/XInclude\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.imsglobal.org/xsd/imsqtiasi_v3p0 https://purl.imsglobal.org/spec/qti/v3p0/schema/xsd/imsqti_asiv3p0_v1p0.xsd\">\r\n"
				+ "    <qti-response-declaration base-type=\"identifier\" cardinality=\"single\" identifier=\"RESPONSE\">\r\n"
				+ "  <qti-correct-response>\r\n" + "	   <qti-value>" + correctAnswer.getId() + "</qti-value>\r\n"
				+ "  </qti-correct-response>\r\n" + "    </qti-response-declaration>\r\n"
				+ "    <qti-outcome-declaration base-type=\"integer\" cardinality=\"single\" identifier=\"SCORE\"/>\r\n"
				+ "      <qti-default-value>\r\n" + "	       <qti-value>" + question.getPoints() + "</qti-value>\r\n"
				+ "      </qti-default-value>\r\n" + "    </qti-outcome-declaration>\r\n" + "    <qti-item-body>\r\n"
				+ "	     <qti-choice-interaction max-choices=\"1\" response-identifier=\"RESPONSE\" shuffle=\"true\">"
				+ "        <qti-prompt>" + question.getQuestion() + "        </qti-prompt>\r\n";

		for (Answer answer : question.getAnswers()) {
			questionXML += "         <qti-simple-choice fixed=\"false\" identifier=\"" + answer.getId() + "\">"
					+ answer.getAnswer() + "</qti-simple-choice>\r\n";
		}

		questionXML += "      </qti-choice-interaction>\r\n" + "</qti-item-body>\r\n"
				+ "<qti-response-processing template=\"\"/>\r\n" + "</qti-assessment-item>";

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("QTI/test-" + test.getId().toString()
					+ "-QTI/questions/" + "test" + test.getId().toString() + "-" + questionNumber + ".xml"));
			writer.write(questionXML);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean makeFolders(Long id) {

		if (!new File("QTI").exists()) {
			File folderQTI = new File("QTI");
			Boolean b = folderQTI.mkdir();
		}

		// Creating a File object
		File folder1 = new File("QTI/test-" + id.toString() + "-QTI");
		// Creating the directory
		boolean bool = folder1.mkdir();
		if (bool) {
			File folder2 = new File("QTI/test-" + id.toString() + "-QTI" + "/questions");
			// Creating the directory
			boolean bool2 = folder2.mkdir();

			if (bool2) {
				System.out.println("Directory created successfully");
			} else {
				System.out.println("Nije dobro kreiran drugi folder");
				return false;
			}
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		return bool;
	}

	private void zipDirectory(File dir, String zipDirName) {
		try {
			List<String> filesListInDir = new ArrayList<String>();

			populateFilesList(filesListInDir, dir);

			FileOutputStream fos = new FileOutputStream(zipDirName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String filePath : filesListInDir) {
				System.out.println("Zipping " + filePath);
				ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);

				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Pomocna funkcija za zipDirectory funkciju
	private void populateFilesList(List<String> filesListInDir, File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(filesListInDir, file);
		}
	}

	public byte[] convertFileContentToBlob(File file) throws IOException {
		// create file object
		// File file = new File(filePath);
		// initialize a byte array of size of the file
		byte[] fileContent = new byte[(int) file.length()];
		FileInputStream inputStream = null;
		try {
			// create an input stream pointing to the file
			inputStream = new FileInputStream(file);
			// read the contents of file into byte array
			inputStream.read(fileContent);
		} catch (IOException e) {
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		} finally {
			// close input stream
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return fileContent;
	}

}