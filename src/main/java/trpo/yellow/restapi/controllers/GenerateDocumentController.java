package trpo.yellow.restapi.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trpo.yellow.restapi.controllers.dto.DocumentData;
import trpo.yellow.restapi.controllers.dto.DocumentExtension;
import trpo.yellow.restapi.services.DocumentGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController()
public class GenerateDocumentController {

    private final DocumentGenerator documentGenerator;

    @Autowired
    public GenerateDocumentController(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    @PostMapping(value = "generate", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value = "Генерация шаблона документа")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Если набор параметров не валиден"),
            @ApiResponse(code = 500, message = "Ошибка на сервере")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "documentParams",
                    value = "Параметры генерируемого файла",
                    required = true,
                    paramType = "body"
            ),
            @ApiImplicitParam(
                    name = "documentExtension",
                    value = "Расширение генерируемого файла",
                    required = true,
                    paramType = "path"
            )
    })
    public ResponseEntity<byte[]> generate(@RequestBody DocumentData documentData,
                                           @RequestParam(name = "document_extension") DocumentExtension documentExtension) throws IOException {
        MediaType responseContentType = documentExtension == DocumentExtension.PDF ? MediaType.APPLICATION_PDF : MediaType.TEXT_PLAIN;
        return ResponseEntity
                .ok()
                .contentType(responseContentType)
                .body(Files.readAllBytes(documentGenerator.generateDocument(documentData, documentExtension).toPath()));
    }
}
