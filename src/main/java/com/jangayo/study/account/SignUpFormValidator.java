package com.jangayo.study.account;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator  implements Validator{
    
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub

        SignUpForm signupform = (SignUpForm) target;

        if (accountRepository.existsByEmail(signupform.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[] { signupform.getEmail() }, "이미 사용중인 이메일 입니다.");

        }

        if (accountRepository.existsByNickname(signupform.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[] { signupform.getNickname() }, "이미 사용중인 아이디 입니다.");

        }
        
    }

    
}
