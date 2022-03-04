package com.jangayo.study.account;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByNickname(String username);


    // @Query(nativeQuery = true, 
    // value = "SELECT id, rolename FROM account_role")
    // HashMap<Long, String> AllRoles();
}
