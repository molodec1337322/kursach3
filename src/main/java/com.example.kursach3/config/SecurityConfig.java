package com.example.kursach3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@ComponentScan(basePackages = "com.example.kursach3")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/tickets").permitAll()
                .antMatchers("/auth/registration", "/auth/login", "/files/*").not().fullyAuthenticated()
                //.antMatchers("/workers/new", "/subjects/new").hasRole("USER")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                // ?????????????????? ???????????????? ?? ???????????? ????????????
                .loginPage("/auth/login")
                .defaultSuccessUrl("/tickets/redirect", true)
                // ?????????????????? action ?? ?????????? ????????????
                .loginProcessingUrl("/auth/login_processing")
                // ?????????????????? URL ?????? ?????????????????? ????????????
                .failureUrl("/auth/login?error")
                // ?????????????????? ?????????????????? ???????????? ?? ???????????? ?? ?????????? ????????????
                .usernameParameter("email")
                .passwordParameter("password")
                // ???????? ???????????? ?? ?????????? ???????????? ????????
                .permitAll();
    }
}