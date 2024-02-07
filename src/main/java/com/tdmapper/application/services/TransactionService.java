package com.tdmapper.application.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tdmapper.application.models.Transaction;
import com.tdmapper.application.repositorys.TransactionRepository;

@Service
public class TransactionService {
private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Optional<Transaction> get(Long id) {
        return repository.findById(id);
    }

    public Transaction update(Transaction entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Transaction> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Transaction> list(Pageable pageable, Specification<Transaction> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}
