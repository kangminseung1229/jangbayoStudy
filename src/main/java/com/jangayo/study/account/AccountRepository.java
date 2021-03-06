package com.jangayo.study.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByNickname(String username);


}
