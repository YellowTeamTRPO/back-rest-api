package trpo.yellow.restapi.services.impl;

import org.springframework.stereotype.Service;
import trpo.yellow.restapi.model.GeneratedDocument;
import trpo.yellow.restapi.services.DocumentGenerator;

import java.io.File;

@Service
public class DocumentGeneratorImpl implements DocumentGenerator {
    @Override
    public File generateDocument() {
        return null;
    }
}
