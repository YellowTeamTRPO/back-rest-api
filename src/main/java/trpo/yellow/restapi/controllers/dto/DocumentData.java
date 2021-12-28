package trpo.yellow.restapi.controllers.dto;

import lombok.Data;

@Data
public class DocumentData {
    private String institute;
    private String higherSchool;
    private String discipline;
    private String type;
    private Integer number;
    private String topic;
    private Integer variant;
    private String studentName;
    private String teacherName;
    private String groupNumber;
    private Integer year;
}
