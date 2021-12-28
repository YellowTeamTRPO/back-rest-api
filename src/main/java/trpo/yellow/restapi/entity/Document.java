package trpo.yellow.restapi.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;
import java.util.UUID;

@Entity
@Data
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(generator = "id_generator")
    @GenericGenerator(name = "id_generator", strategy = "uuid2")
    @Column(name = "id", unique = true, updatable = false)
    @ColumnDefault("random_uuid()")
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "year")
    private Integer year;

    @Lob
    @Column(name = "template")
    private byte[] template;
}
