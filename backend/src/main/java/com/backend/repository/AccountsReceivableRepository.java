package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.AccountsReceivable;

import java.util.List;

@Repository
public interface AccountsReceivableRepository extends JpaRepository<AccountsReceivable, String> {
    List<AccountsReceivable> findByStatus(AccountsReceivable.Status status);
}
