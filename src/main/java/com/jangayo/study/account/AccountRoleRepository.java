package com.jangayo.study.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRoleRepository extends JpaRepository<AccountRole,Long> {


    // SELECT role.rolename FROM account user
    // inner join authority auth on user.id = auth.account_id
    // inner join account_role role on role.id = auth.role_id
    // where user.nickname = 'jang';

    
}
