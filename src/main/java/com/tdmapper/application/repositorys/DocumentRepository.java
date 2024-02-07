package com.tdmapper.application.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tdmapper.application.models.Document;

public interface DocumentRepository extends
            JpaRepository<Document, Long>,
            JpaSpecificationExecutor<Document> {

}
