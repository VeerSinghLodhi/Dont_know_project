package com.example.ProjectHON.SecurityPackage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    UserSuccessHandler userSuccessHandler;

    @Autowired
    OauthAuthenticationSuccessHandler oauthAuthenticationSuccessHandler;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        System.out.println("DaoAuthenticationProvider Method");
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        System.out.println("Security Filter Chain Method");
        httpSecurity.authorizeHttpRequests(http -> {
            http.requestMatchers("/login", "/signup").permitAll()
                    .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated();
        });


        httpSecurity.csrf(csrf -> csrf.disable());


        httpSecurity.formLogin(login->{

            login.loginPage("/login");
            login.loginProcessingUrl("/do-login");
            login.successHandler(userSuccessHandler);

            login.failureUrl("/login?error=true");

            login.usernameParameter("username");
            login.passwordParameter("password");
            login.permitAll();
        });

        httpSecurity.logout(logout->{
            logout.permitAll();
        });


        // Oauth Configuration

//        httpSecurity.oauth2Login(Customizer.withDefaults());

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oauthAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
