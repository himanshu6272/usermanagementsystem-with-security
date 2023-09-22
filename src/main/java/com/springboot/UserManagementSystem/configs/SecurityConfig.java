package com.springboot.UserManagementSystem.configs;

import com.springboot.UserManagementSystem.Exceptions.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private static final Logger log = Logger.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsService oauthUserService;

    @Bean
    public UserDetailsService getUserDetailService(){
        return new UserDetailsServiceImpl();
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                        .requestMatchers("/admin", "/edit/**").hasAuthority("ADMIN").requestMatchers("/viewPage", "/edit/**").hasAuthority("USER")
                        .requestMatchers("/login", "/", "/home", "/forgot", "/forgotPassword", "/reset/**", "/register", "/create", "/editUser", "/loginUser", "/resetPassword").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").loginProcessingUrl("/login").successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                String email = (String) authentication.getPrincipal();
                                request.getSession().setAttribute("email", email);
                                response.sendRedirect("/loginUser");
                            }
                        })
//                        .defaultSuccessUrl("/loginUser")
                        .failureHandler(new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                request.getSession().setAttribute("message", new ApiResponse("Bad credentials", null, "alert-danger"));
                                response.sendRedirect("/login");
                            }
                        })
                        .permitAll()

                )
                .oauth2Login((oauth2)-> oauth2.loginPage("/login").userInfoEndpoint((userInfoEndpointConfig)->userInfoEndpointConfig.userService(oauthUserService)).successHandler((AuthenticationSuccessHandler) new AuthenticationSuccessHandler(){

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        OauthCustomUserDetails oauthCustomUserDetails = (OauthCustomUserDetails) authentication.getPrincipal();
                        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                        String email = oauth2User.getAttribute("email");
                        log.info(email);
                        request.getSession().setAttribute("email", email);
                        response.sendRedirect("/loginUser");

                    }
                }).failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        request.getSession().setAttribute("message", new ApiResponse("Bad credentials", null, "alert-danger"));
                        response.sendRedirect("/login");
                    }
                }))
                .logout((logout) -> logout.logoutSuccessUrl("/logoutUser").permitAll());

        return http.build();
    }
}
