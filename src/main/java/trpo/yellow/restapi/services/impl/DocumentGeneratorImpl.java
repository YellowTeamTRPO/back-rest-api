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

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class DocumentGeneratorImpl implements DocumentGenerator {

    private DocumentRepository repository;

    @Autowired
    public DocumentGeneratorImpl(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public File generateDocument(DocumentData documentData, DocumentExtension documentExtension) {
        File template = getDocumentTemplate(documentData);
        return documentExtension == DocumentExtension.PDF ?
                generatePDF(template, documentData) : generateTEX(template, documentData);
    }

    private File generateTEX(File template, DocumentData documentData) {

        return template;
    }

    private File generatePDF(File template, DocumentData documentData) {
        return template;
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
