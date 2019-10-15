package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author Windows 10
 */
@Configuration     // Tags the class as a source of bean definitions for the application context.
@EnableWebSecurity // Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
// integrate Spring Security + Spring MVC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/static/**", "/resources/**", "/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/account**").hasAnyRole("user", "admin")
                //.antMatchers("/anonymous*").anonymous() // Allow anonymous access on /login so that users can authenticate.
                //.antMatchers("/login*").permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // The custom login page
                .usernameParameter("email") // Login Form have 2 fields with name='email' & name = 'password'
                .passwordParameter("password")
                //.loginProcessingUrl("/check-login ")   // The url to submit the username and password to
                //.defaultSuccessUrl("/account", true)
                .successForwardUrl("/account")
                .failureForwardUrl("/account")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //Spring Security provides a component that has the direct responsibility of deciding what to do after a successful authentication
    // – the AuthenticationSuccessHandler.
//    @Bean
//    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
//        return new MySimpleUrlAuthenticationSuccessHandler();
//    }
}
