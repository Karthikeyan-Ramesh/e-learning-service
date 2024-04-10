package com.karthikeyan.eLearning.configuration;

import com.karthikeyan.eLearning.constant.LearningConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(configure -> {
                    try {
                        configure
                                .antMatchers(HttpMethod.GET, "/api/courses").hasRole(LearningConstant.STUDENT)
                                .antMatchers(HttpMethod.POST, "/api/courses").hasRole(LearningConstant.MANAGER)
                                .antMatchers(HttpMethod.POST, "/api/students").hasRole(LearningConstant.STUDENT)
                                .antMatchers(HttpMethod.GET, "/api/students/**").hasRole(LearningConstant.STUDENT)
                                .antMatchers("/api/**").hasRole(LearningConstant.ADMIN)
                                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        //basic authenticate
        httpSecurity.httpBasic(Customizer.withDefaults());

        //disable crsf
        httpSecurity.csrf(csrf -> csrf.disable());
        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

}
