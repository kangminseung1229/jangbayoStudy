package com.jangayo.study.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRoleRepository extends JpaRepository<AccountRole,Long> {

    
    @Query(value = "select role_id from authority where account_id = :id", nativeQuery = true)
    List<Long> getRoles(Long id);
}
