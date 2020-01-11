package com.nf.yy.config.security;

import com.nf.yy.config.security.filter.JwtAuthenticationTokenFilter;
import com.nf.yy.config.security.filter.VerificationCodeFilter;
import com.nf.yy.config.security.handler.CustomAccessDeniedHandler;
import com.nf.yy.config.security.handler.EntryPointUnauthorizedHandler;
import com.nf.yy.config.security.handler.LoginFailureHandler;
import com.nf.yy.config.security.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 配置SpringSecurity
 *
 * @author smile
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.nf.yy.config.security")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 自定义UserDetailsService */
    @Autowired
    private UserDetailsService userDetailsService;
    /** jwt令牌过滤器 */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    /** 自定义未授权异常处理器 */
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    /** 自定义身份校验失败处理器 */
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    /** 自定义登录失败处理器 */
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    /** 自定义登录成功处理器 */
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    /** 验证码过滤器 */
    @Autowired
    private VerificationCodeFilter verificationCodeFilter;

    /** 密码编码器 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    /**
     * 配置AuthenticationManagerBuilder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // @formatter:off
    /**
     * 配置HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf().disable()
            .cors().configurationSource(urlBasedCorsConfigurationSource())
        .and()
            .formLogin()
            .loginProcessingUrl("/user/login")
            .usernameParameter("userId")
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler)
        .and()
            .exceptionHandling()
            .authenticationEntryPoint(entryPointUnauthorizedHandler)
            .accessDeniedHandler(customAccessDeniedHandler)
        .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/api/user/information").authenticated()
            .antMatchers("/api/**").access("@customAuthorizationManager.authorizationManager(authentication,request)")
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    // @formatter:on

    @Bean
    public UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://127.0.0.1:8848");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("HEAD");
        // 设置客户端允许的头,jwt的令牌是通过header传递过来的
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
