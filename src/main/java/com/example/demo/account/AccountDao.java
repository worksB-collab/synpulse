package com.example.demo.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.user.userId = :userId")
    Optional<List<Account>> findByUserId(final String userId);
}
