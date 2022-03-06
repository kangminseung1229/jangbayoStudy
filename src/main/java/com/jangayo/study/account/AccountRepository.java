package com.jangayo.study.account;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByNickname(String username);

    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);

    // @Query(nativeQuery = true, 
    // value = "SELECT id, rolename FROM account_role")
    // HashMap<Long, String> AllRoles();
}
