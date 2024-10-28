package com.eatwell.foodHQ.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/","/js/*").permitAll()
                //securing endpoints with roles
                .antMatchers(HttpMethod.GET,"/api/booker/**").hasAnyRole(ApplicationUserRole.CUSTOMER.name(),ApplicationUserRole.ADMINTRAINEE.name())
                .antMatchers(HttpMethod.POST,"/api/booker/**").hasRole(ApplicationUserRole.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/api/booker/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
                .antMatchers(HttpMethod.DELETE,"/api/booker/**").hasRole(ApplicationUserRole.ADMIN.name())
                //securing endpoints with permission/authority
                .antMatchers(HttpMethod.GET,"/api/booking/**").hasAuthority(ApplicationUserPermission.RESERVATION_READ.getPermission())
                .antMatchers(HttpMethod.POST,"/api/booking/**").hasAuthority(ApplicationUserPermission.RESERVATION_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/booking/**").hasAuthority(ApplicationUserPermission.RESERVATION_UPDATE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/api/booking/**").hasAuthority(ApplicationUserPermission.RESERVATION_DELETE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder () {
      return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails customer = User.builder()
                .username("Sarah Smith")
                .password(passwordEncoder().encode("password"))
                //.roles(ApplicationUserRole.CUSTOMER.name())
                .authorities(ApplicationUserRole.CUSTOMER.getGrantedAuthorities())
                .build();
        UserDetails admin = User.builder()
                .username("Linda Smith")
                .password(passwordEncoder().encode("password"))
                //.roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails adminTrainee = User.builder()
                .username("Tom Smith")
                .password(passwordEncoder().encode("password"))
                //.roles(ApplicationUserRole.ADMINTRAINEE.name())
                .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(customer,admin,adminTrainee);
    }
}
