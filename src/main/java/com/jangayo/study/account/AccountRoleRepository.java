package com.jangayo.study.account;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRoleRepository extends JpaRepository<AccountRole,Long> {


    // SELECT role.rolename FROM account user
    // inner join authority auth on user.id = auth.account_id
    // inner join account_role role on role.id = auth.role_id
    // where user.nickname = 'jang';

    
    @Query(value = "select role_id from authority where account_id = :id", nativeQuery = true)
    List<Long> getRoles(Long id);

    //체크한 권한

    List<AccountRole> findByIdIn(List<Long> roleIds);

}
