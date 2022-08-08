package com.dimakar.codesharing.repository;

import com.dimakar.codesharing.model.CodeSnippetEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodeSnippetRepository extends CrudRepository<CodeSnippetEntity, UUID> {
    CodeSnippetEntity findByUid(UUID id);
    List<CodeSnippetEntity> findAllByHasRestriction(Pageable pageable, boolean hasRestriction);
}
