package com.example.azure;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.azure.spring.cloud.autoconfigure.aad.AadWebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AzureAdOAuthLoginSecurityConfig extends AadWebSecurityConfigurerAdapter {

    // https://learn.microsoft.com/ja-jp/azure/developer/java/spring-framework/spring-boot-starter-for-azure-active-directory-developer-guide#protect-a-resource-serverapi
    // 独自のセキュリティ構成にしたい場合は、AadWebSecurityConfigurerAdapterを継承して指定する
    // (デフォルトはDefaultAadResourceServerWebSecurityConfigurerAdapterで構成したものになっている)

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        // https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
        http.authorizeHttpRequests(
                authz -> authz
                        // admin配下は要adminロール
                        .mvcMatchers("/admin/**").hasAnyAuthority("APPROLE_admin")
                        // user配下は要userロール
                        .mvcMatchers("/user/**").hasAnyAuthority("APPROLE_user")
                        // それ以外のリクエストも全て認証必要(ロールは何でもOK(無くてもOK))
                        .anyRequest().authenticated());

        http.logout(
                // GETでもログアウトが受け付けられるように
                logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")));
    }
}