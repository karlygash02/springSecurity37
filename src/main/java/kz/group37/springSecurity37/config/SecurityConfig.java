package kz.group37.springSecurity37.config;

import kz.group37.springSecurity37.services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthUserService authUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().accessDeniedPage("/accesserror");

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll();

        http.formLogin()
                .loginProcessingUrl("/toenter").permitAll() // <- where to send post request to sign in
                .loginPage("/signin").permitAll()  // <- which page is a default page to sign in (from where to send request)
                .usernameParameter("user_email")  // <- <input type = 'email' name = 'user_email'>
                .passwordParameter("user_password") // <- <input type = 'password' name = 'user_password'>
                .defaultSuccessUrl("/profile") // <- where to redirect after success sign in process
                .failureUrl("/signin?error");  // <- where to redirect after unsuccessful sing in process

        http.logout()
                .logoutSuccessUrl("/signin")  //after clicking to logout button where to go back
                .logoutUrl("/toexit");  // url endpoint to sign out

        http.csrf().disable();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
