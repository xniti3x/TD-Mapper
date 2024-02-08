package com.tdmapper.application.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tdmapper.application.models.Transaction;

public interface TransactionRepository extends
            JpaRepository<Transaction, Long>,
            JpaSpecificationExecutor<Transaction> {
                
                Page<Transaction> findByAmountIsNotNull(org.springframework.data.domain.Pageable pageable);
                
}
