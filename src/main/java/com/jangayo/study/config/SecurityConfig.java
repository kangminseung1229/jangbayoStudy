package com.jangayo.study.config;
import javax.sql.DataSource;

import com.jangayo.study.account.AccountService;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final AccountService accountService;
    private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .mvcMatchers("/","/error", "/login","/account/sign-up","/account/check-email-token").permitAll()
            .anyRequest().authenticated();

            
        // form login
        http.formLogin()
            .loginPage("/login").permitAll();
            

        // form logout
        http.logout()
            .logoutSuccessUrl("/");

        //remember me
        http.rememberMe()
            .userDetailsService(accountService)
            .tokenRepository(tokenRepository());

    }

    @Bean
    public PersistentTokenRepository tokenRepository() {

        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl(); // 해당 상위 클래스의 쿼리문에 해당하는 클래스를 생성해주어야 한다.
        jdbcTokenRepositoryImpl.setDataSource(dataSource);

        System.out.println("========================쿠키생성========================");
        System.out.println(jdbcTokenRepositoryImpl);
        System.out.println("========================쿠키생성========================");

        return jdbcTokenRepositoryImpl;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO Auto-generated method stub

        web.ignoring()
            .mvcMatchers("/css/**")
            .mvcMatchers("/node_modules/**")
            .mvcMatchers("/img/**")
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
}
