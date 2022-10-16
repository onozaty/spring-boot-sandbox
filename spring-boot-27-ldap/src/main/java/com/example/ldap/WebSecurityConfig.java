package com.example.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.LdapPasswordComparisonAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
        LdapPasswordComparisonAuthenticationManagerFactory factory =
                new LdapPasswordComparisonAuthenticationManagerFactory(
                        contextSource, new BCryptPasswordEncoder());
        factory.setUserDnPatterns("uid={0},ou=people");
        factory.setPasswordAttribute("userPassword");
        return factory.createAuthenticationManager();
    }

    @Bean
    public BaseLdapPathContextSource contextSource() {
        return new DefaultSpringSecurityContextSource("ldap://localhost:8389/dc=example,dc=com");
    }
}