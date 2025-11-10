package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.AccountsPayable;

import java.util.List;

@Repository
public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, String> {
    List<AccountsPayable> findByStatus(AccountsPayable.Status status);
}
