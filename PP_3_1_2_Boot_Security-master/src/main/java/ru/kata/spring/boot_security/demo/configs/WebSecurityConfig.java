package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.kata.spring.boot_security.demo.model.Permission;
import ru.kata.spring.boot_security.demo.model.Role;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers( HttpMethod.GET , "/user/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())
                .antMatchers( HttpMethod.GET , "/admin/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                .permitAll();

    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .authorities(Role.USER.getAuthorites())
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .authorities(Role.ADMIN.getAuthorites())
                        .build();

        return new InMemoryUserDetailsManager(user , user2);
    }
}