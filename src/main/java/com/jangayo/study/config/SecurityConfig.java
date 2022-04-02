package com.jangayo.study.config;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{


    private final UserDetailsService accountService;
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

        //remember-me
        http.rememberMe()
                .userDetailsService(accountService)
                .tokenRepository(tokenRepository());

    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);

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
