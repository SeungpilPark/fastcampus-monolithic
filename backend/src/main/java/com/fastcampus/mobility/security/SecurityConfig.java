package com.fastcampus.mobility.security;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@ComponentScan(basePackages = "com.fastcampus.mobility.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String[] WEB_IGNORE_PATHS = new String[]{
      "/static/**", "/favicon.ico", "/index.html", "/error", "/health",
      "/login", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs"
  };

  public static final String[] API_PATHS = new String[]{
      "/api/**"
  };
  public static final String[] COMMON_API_PATHS = new String[]{
      "/api/security/**"
  };

  public static boolean isApiPath(HttpServletRequest request) {
    return new AntPathRequestMatcher("/api/**").matches(request);
  }

  private final PasswordEncoder passwordEncoder;
  private final CustomUserDetailsService customUserDetailsService;
  private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
  private final CustomAuthenticationFailureHandler authenticationFailureHandler;
  private final CustomLogoutHandler customLogoutHandler;
  private final ClientAuthenticationFilter clientAuthenticationFilter;
  private final MdcTrackingFilter mdcTrackingFilter;
  private final SecurityErrorResponseHandler errorResponseHandler;
  private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @Autowired
  public SecurityConfig(
      final PasswordEncoder passwordEncoder,
      final CustomUserDetailsService customUserDetailsService,
      final CustomAuthenticationSuccessHandler authenticationSuccessHandler,
      final CustomAuthenticationFailureHandler authenticationFailureHandler,
      final CustomLogoutHandler customLogoutHandler,
      final ClientAuthenticationFilter clientAuthenticationFilter,
      final MdcTrackingFilter mdcTrackingFilter,
      final SecurityErrorResponseHandler errorResponseHandler,
      final CustomLogoutSuccessHandler customLogoutSuccessHandler
  ) {
    this.passwordEncoder = passwordEncoder;
    this.customUserDetailsService = customUserDetailsService;
    this.authenticationSuccessHandler = authenticationSuccessHandler;
    this.authenticationFailureHandler = authenticationFailureHandler;
    this.customLogoutHandler = customLogoutHandler;
    this.clientAuthenticationFilter = clientAuthenticationFilter;
    this.mdcTrackingFilter = mdcTrackingFilter;
    this.errorResponseHandler = errorResponseHandler;
    this.customLogoutSuccessHandler = customLogoutSuccessHandler;
  }

  @SuppressWarnings("RedundantThrows")
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(WEB_IGNORE_PATHS);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
      errorResponseHandler.send(response, "로그인이 필요합니다.");
    });

    http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
      errorResponseHandler.send(response, "접근이 허용되지 않은 사용자입니다.");
    });

    http.headers();
    http.csrf().disable();
    http.cors().disable();
    http
        .authorizeRequests()
        .antMatchers(COMMON_API_PATHS).permitAll()
        .antMatchers(API_PATHS).authenticated()
        .anyRequest().permitAll()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/api/security/login")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .and()
        .logout()
        .logoutUrl("/api/security/logout")
        .addLogoutHandler(customLogoutHandler)
        .deleteCookies("SESSION")
        .invalidateHttpSession(true)
        .logoutSuccessHandler(customLogoutSuccessHandler);

    http
        .addFilterAfter(clientAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(mdcTrackingFilter, ClientAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsServiceBean())
        .passwordEncoder(passwordEncoder);
  }

  @Override
  public UserDetailsService userDetailsServiceBean() {
    return customUserDetailsService;
  }
}
