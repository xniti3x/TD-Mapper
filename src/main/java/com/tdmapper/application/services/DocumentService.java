package com.tdmapper.application.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tdmapper.application.models.Document;
import com.tdmapper.application.repositorys.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository repository;
    
        public DocumentService(DocumentRepository repository) {
            this.repository = repository;
        }
    
        public Optional<Document> get(Long id) {
            return repository.findById(id);
        }
    
        public Document update(Document entity) {
            return repository.save(entity);
        }
    
        public void delete(Long id) {
            repository.deleteById(id);
        }
    
        public Page<Document> list(Pageable pageable) {
            return repository.findAll(pageable);
        }
    
        public Page<Document> list(Pageable pageable, Specification<Document> filter) {
            return repository.findAll(filter, pageable);
        }
    
        public int count() {
            return (int) repository.count();
        }
    }