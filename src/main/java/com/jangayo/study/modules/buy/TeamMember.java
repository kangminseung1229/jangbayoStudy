package com.jangayo.study.modules.buy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class TeamMember {

    @Id
    private Long id;

    private String name;

    private int age;

    private String sex;
    
    @ManyToOne
    private Team team;

    
}
