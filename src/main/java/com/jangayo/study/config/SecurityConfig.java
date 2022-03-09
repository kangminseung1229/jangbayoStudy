package com.jangayo.study.config;
import org.hibernate.type.TrueFalseType;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .mvcMatchers("/","/error", "/login","/account/sign-up").permitAll()
            .anyRequest().authenticated();

            
        // form login
        http.formLogin()
            .loginPage("/login").permitAll();
            

        // form logout
        http.logout()
            .logoutSuccessUrl("/");

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
