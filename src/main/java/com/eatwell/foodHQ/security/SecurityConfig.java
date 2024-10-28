package com.eatwell.foodHQ.security;

//import com.eatwell.foodHQ.auth.ApplicationUserService;
import com.eatwell.foodHQ.jwt.JwtConfig;
import com.eatwell.foodHQ.jwt.JwtTokenVerifier;
import com.eatwell.foodHQ.jwt.JwtUserNamePasswordAuthenticationFilter;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final ApplicationUserService applicationUserService;
//    //private PasswordEncoder passwordEncoder;
//    @Autowired
//    public SecurityConfig(ApplicationUserService applicationUserService) {
//        this.applicationUserService = applicationUserService;
//        //this.passwordEncoder = passwordEncoder;
//    }
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Autowired
    public SecurityConfig(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrf().disable()
                //.and()
                //to configure jwt as a stateless authentication/ ensuring session is not stored in the db as it was previously.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.addFilter(new JwtUserNamePasswordAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtUserNamePasswordAuthenticationFilter(authenticationManager(),jwtConfig,secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey,jwtConfig),JwtUserNamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","index","/css/","/js/*").permitAll()
                // securing endpoints with roles
                .antMatchers(HttpMethod.GET,"/api/booker/**").hasAnyRole(ApplicationUserRole.CUSTOMER.name(),ApplicationUserRole.ADMINTRAINEE.name())
                .antMatchers(HttpMethod.POST,"/api/booker/**").hasRole(ApplicationUserRole.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/api/booker/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
                .antMatchers(HttpMethod.DELETE,"/api/booker/**").hasRole(ApplicationUserRole.ADMIN.name())
                //securing endpoints with permission/authority
//                .antMatchers(HttpMethod.GET,"/api/booking/**").hasAuthority(ApplicationUserPermission.RESERVATION_READ.getPermission())
//                .antMatchers(HttpMethod.POST,"/api/booking/**").hasAnyAuthority(ApplicationUserPermission.RESERVATION_WRITE.getPermission(),ApplicationUserPermission.CUSTOMER_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/api/booking/**").hasAnyAuthority(ApplicationUserPermission.RESERVATION_UPDATE.getPermission(),ApplicationUserPermission.CUSTOMER_UPDATE.getPermission())
//                .antMatchers(HttpMethod.DELETE,"/api/booking/**").hasAnyAuthority(ApplicationUserPermission.RESERVATION_DELETE.getPermission(),ApplicationUserPermission.CUSTOMER_DELETE.getPermission())
                .anyRequest()
                .authenticated();
//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .defaultSuccessUrl("/foodMenu",true)
////                    .passwordParameter("password")
////                    .usernameParameter("UserName")
//                .and()
//                .rememberMe()//sessions expire after 30 minutes of inactivity.However, rememberMe() by default,is valid for 2wks
//                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))//customise remember me to exceed its default of 2 weeks
//                    .key("secured")
////                    .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))//we should use a POST request to logout instead of a GET request but because we have disabled csrf, we will use a GET request
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID","remember-me")
//                    .logoutSuccessUrl("/login");
                    //.httpBasic();
        System.out.println();

    }


    @Bean
    PasswordEncoder passwordEncoder1 () {
        return new BCryptPasswordEncoder();
    }
//    @Override
//    protected void configure (AuthenticationManagerBuilder auth) throws  Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider () {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder1());
//        provider.setUserDetailsService(applicationUserService);
//        return provider;
//    }



    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails customer = User.builder()
                .username("Sarah Smith")
                .password(passwordEncoder1().encode("password"))
                .roles(ApplicationUserRole.CUSTOMER.name())
                //.authorities(ApplicationUserRole.CUSTOMER.getGrantedAuthorities())
                .build();
        UserDetails admin = User.builder()
                .username("Linda Smith")
                .password(passwordEncoder1().encode("password"))
                .roles(ApplicationUserRole.ADMIN.name())
                //.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails adminTrainee = User.builder()
                .username("Tom Smith")
                .password(passwordEncoder1().encode("password"))
                .roles(ApplicationUserRole.ADMINTRAINEE.name())
                //.authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(customer,admin,adminTrainee);
    }
}