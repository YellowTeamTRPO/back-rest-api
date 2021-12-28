package trpo.yellow.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import trpo.yellow.restapi.entity.Document;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends CrudRepository<Document, UUID> {
    Optional<Document> findByTypeAndAndYear(String type, Integer year);
}
