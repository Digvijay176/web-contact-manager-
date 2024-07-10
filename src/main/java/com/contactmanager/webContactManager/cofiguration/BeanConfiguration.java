package com.contactmanager.webContactManager.cofiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BeanConfiguration {

    @Bean
    UserDetailsService getUserDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Bean
    PasswordEncoder getpassEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain fiterChain(HttpSecurity http) throws Exception {
           
           http.csrf((csrf)->csrf.disable());
           
           http
           .authorizeHttpRequests((authz) ->
                authz
                .requestMatchers("/","/contactmanager/public/**","/images/**","/images/profile.jpg").permitAll()
                .requestMatchers(
                		"/user/dashboard",
                		"/user/add-contact",
                		"/user/contact-handler",
                		"/user/view-contact",
                		"/user/contact/delete/**",
                		"/user/contact/update/**",
                		"/user/contact/update-handler",
                		"/user/profile-page",
                		"/user/contact/profile/update/**"
                		).hasRole("USER")
                .requestMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
           )
//           .formLogin(Customizer.withDefaults());
           .formLogin(form-> form.loginPage("/login").permitAll().loginProcessingUrl("/dologin").successForwardUrl("/user/dashboard"));
           
        return http.build();

    }
}
