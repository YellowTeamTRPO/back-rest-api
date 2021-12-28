package trpo.yellow.restapi.services.impl;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trpo.yellow.restapi.controllers.dto.DocumentData;
import trpo.yellow.restapi.controllers.dto.DocumentExtension;
import trpo.yellow.restapi.entity.Document;
import trpo.yellow.restapi.exception.HttpRequestException;
import trpo.yellow.restapi.repository.DocumentRepository;
import trpo.yellow.restapi.services.DocumentGenerator;

import java.io.*;
import java.util.Optional;

@Service
public class DocumentGeneratorImpl implements DocumentGenerator {

    private static final String GENERATED_TEX_FILE_NAME = "generatedTex.tex";
    private static final String RESOURCES_PATH = "src/main/resources/templates/";

    private DocumentRepository repository;

    @Autowired
    public DocumentGeneratorImpl(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public File generateDocument(DocumentData documentData, DocumentExtension documentExtension) {
        File template = getDocumentTemplate(documentData);
        String parsedTemplate = parseTemplate(template, documentData);
        File generatedTex = generateTEX(parsedTemplate);
        return documentExtension == DocumentExtension.PDF ?
                generatePDF(generatedTex) : generatedTex;
    }

    private String parseTemplate(File template, DocumentData documentData) {
        StringBuilder texFile = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(template));

            String line;
            while ((line = br.readLine()) != null) {
                line = parseTexLine(line, documentData);
                texFile.append(line);
                texFile.append(System.getProperty("line.separator"));
            }
            br.close();

        } catch (IOException e) {
            throw new HttpRequestException();
        }
        return texFile.toString();
    }

    private static String parseTexLine(String line, DocumentData documentData) {
        String[] strArr = line.split(" % ");
        String text = "";
        if (strArr.length > 1) {
            switch (strArr[1]) {
                case "Институт":
                    text = documentData.getInstitute();
                    break;
                case "Высшая школа":
                    text = documentData.getHigherSchool();
                    break;
                case "Вид работы":
                    text = documentData.getType();
                    break;
                case "Номер работы":
                    text = documentData.getNumber().toString();
                    break;
                case "Дисцплина":
                    text = documentData.getDiscipline();
                    break;
                case "Тема есть?":
                    text = !documentData.getTopic().isEmpty() ? strArr[0] : "%" + strArr[0];
                    break;
                case "Тема":
                    text = !documentData.getTopic().isEmpty() ? documentData.getTopic() : "%" + strArr[0];
                    break;
                case "Вариант есть?":
                    text = documentData.getVariant() != null ? strArr[0] : "% " + strArr[0];
                    break;
                case "Вариант":
                    text = documentData.getVariant() != null ? documentData.getVariant().toString() : "%" + strArr[0];
                    break;
                case "Студент":
                    text = documentData.getStudentName();
                    break;
                case "Группа":
                    text = documentData.getGroupNumber();
                    break;
                case "Преподаватель":
                    text = documentData.getTeacherName();
                    break;
                case "Год":
                    text = documentData.getYear().toString();
                    break;
                default:
                    text = strArr[0];
            }
            return text + " % " + strArr[1];
        } else {
            return line;
        }
    }

    private File generateTEX(String parsedTemplate) {
        File generatedTex = new File(RESOURCES_PATH + GENERATED_TEX_FILE_NAME);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(generatedTex))) {
            bufferedWriter.write(parsedTemplate);
        } catch (IOException e) {
            throw new HttpRequestException();
        }
        return generatedTex;
    }

    private File generatePDF(File generatedTex) {
        String name = generatedTex.getPath().split(".tex")[0];
        try {
            File file = new File(RESOURCES_PATH + name);
            file.delete();
            ProcessBuilder pb = new ProcessBuilder(
                    "pdflatex", generatedTex.getPath())
                    .inheritIO()
                    .directory(new File(RESOURCES_PATH));
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new File(RESOURCES_PATH + name);
    }

    private File getDocumentTemplate(DocumentData documentData) {
        Optional<Document> documentOptional = repository.findByTypeAndAndYear(documentData.getType(), documentData.getYear());
        File templateFile = new File("src/main/resources/templates/templateFile.tex");
        try {
            Document document = documentOptional.orElseThrow(
                    HttpRequestException::new
            );
            Files.write(document.getTemplate(), templateFile);
        } catch (IOException e) {
            throw new HttpRequestException();
        }
        return templateFile;
    }
}
