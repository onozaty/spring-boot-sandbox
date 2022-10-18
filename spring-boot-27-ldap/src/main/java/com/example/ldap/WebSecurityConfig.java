package com.example.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.LdapPasswordComparisonAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
        // デフォルトのフォーム認証を利用
        http.formLogin();
        http.logout(
                // GETでもログアウトが受け付けられるように
                logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")));

        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
        http.authorizeHttpRequests(
                authz -> authz
                        // admin配下は要ADMINロール
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        // user配下は要USERロール
                        .mvcMatchers("/user/**").hasRole("USER")
                        // それ以外のリクエストも全て認証必要(ロールは何でもOK)
                        .anyRequest().authenticated());

        return http.build();
    }

    // https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/ldap.html
    @Bean
    public BaseLdapPathContextSource contextSource() {
        return new DefaultSpringSecurityContextSource("ldap://localhost:8389/dc=example,dc=com");
    }

    @Bean
    public LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {

        // 権限の情報をgroupsから取得
        String groupSearchBase = "ou=groups";

        DefaultLdapAuthoritiesPopulator authorities =
                new DefaultLdapAuthoritiesPopulator(contextSource, groupSearchBase);
        // uniqueMemberが一致するものが対象
        authorities.setGroupSearchFilter("uniqueMember={0}");

        return authorities;
    }

    @Bean
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource,
            LdapAuthoritiesPopulator authorities) {

        LdapPasswordComparisonAuthenticationManagerFactory factory =
                new LdapPasswordComparisonAuthenticationManagerFactory(contextSource, new BCryptPasswordEncoder());

        // ユーザ名とパスワードのフィールドを指定
        factory.setUserDnPatterns("uid={0},ou=people");
        factory.setPasswordAttribute("userPassword");

        factory.setLdapAuthoritiesPopulator(authorities);

        return factory.createAuthenticationManager();
    }
}