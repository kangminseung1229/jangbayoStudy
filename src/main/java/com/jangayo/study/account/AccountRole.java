package com.jangayo.study.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AccountRole {

    @Id @GeneratedValue
    private Long id;

    private String rolename;

    @Column(unique = true)
    private String role;

}
