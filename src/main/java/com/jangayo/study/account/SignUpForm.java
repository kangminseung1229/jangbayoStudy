package com.jangayo.study.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {


    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")//ㄱ부터 ㅎ 까지, 가부터 힣 까지 _언더바 - 하이푼 {3글자에서 20글자}
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 3, max = 20)
    private String password;

    
    
}
