package com.jangayo.study.modules.buy;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Team {

    @Id
    private Long id;

    private String name;

    
}
