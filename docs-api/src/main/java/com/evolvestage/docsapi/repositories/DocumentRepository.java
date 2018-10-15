package com.evolvestage.docsapi.repositories;

import com.evolvestage.docsapi.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByFileIdAndAndDataIdIsNull(UUID fileId);

    Optional<Document> findByFileId(UUID fileId);

    List<Document> findByFileIdIn(Collection<UUID> fileIds);
}
