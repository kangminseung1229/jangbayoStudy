package com.jangayo.study.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ConsultForm {


    @NotBlank
    @Length(min = 5, max = 30)    
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$")
    private String userid;

    @NotBlank
    @Length(min = 5, max = 400)
    private String consultTitle;


    @NotBlank
    @Length(min = 10)
    private String consultText;

    
}
