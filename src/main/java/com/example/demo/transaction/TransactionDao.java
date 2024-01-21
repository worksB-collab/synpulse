package com.example.demo.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.id = ?1")
    Optional<List<Transaction>> getTransactions(final String userId, final int pageNumber, final int pageSize);

    @Query("SELECT t FROM Transaction t WHERE t.user = :userId")
    Optional<List<Transaction>> findByUserId(final String userId);
}
