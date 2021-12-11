package trpo.yellow.restapi.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trpo.yellow.restapi.controllers.dto.Document;
import trpo.yellow.restapi.controllers.dto.DocumentExtension;
import trpo.yellow.restapi.services.DocumentGenerator;

@RestController("/")
public class GenerateTemplateController {

    private final DocumentGenerator documentGenerator;

    @Autowired
    public GenerateTemplateController(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    @PostMapping("generate")
    @ApiOperation(value = "Генерация шаблона документа")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Если набор параметров не валиден"),
            @ApiResponse(code = 500, message = "Ошибка на сервере")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "documentParams",
                    value = "Параметры генерируемого документа",
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
    public void generate(@RequestBody Document documentParams, @RequestParam DocumentExtension documentExtension) {

    }
}
