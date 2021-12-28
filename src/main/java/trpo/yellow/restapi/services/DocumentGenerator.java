package trpo.yellow.restapi.services;


import trpo.yellow.restapi.controllers.dto.DocumentData;
import trpo.yellow.restapi.controllers.dto.DocumentExtension;
import trpo.yellow.restapi.model.GeneratedDocument;

import java.io.File;

public interface DocumentGenerator {
    File generateDocument(DocumentData documentData, DocumentExtension documentExtension);
}
