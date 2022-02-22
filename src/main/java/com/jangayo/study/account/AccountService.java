package com.jangayo.study.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService{

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입 처리
    public void signUp(SignUpForm signUpForm) {

        Account newAccount = Account.builder()         
                        .nickname(signUpForm.getUserid())
                        .password(passwordEncoder.encode(signUpForm.getPassword()))
                        .build();

        accountRepository.save(newAccount);
    }


    // security DB connect
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        
        Account account = accountRepository.findByNickname(username);

        if(account == null){
            throw new UsernameNotFoundException(username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        account.getRoles().forEach(e -> {
            authorities.add(new SimpleGrantedAuthority(e.getRole()));
        });


        return new UserAccount(account,authorities);
    }

    //로그인 
    public void login(Account account){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new UserAccount(account), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }
    
}
