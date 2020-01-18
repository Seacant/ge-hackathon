package com.hackathon.nku.configurations;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;


  @Autowired
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication()
          .dataSource(dataSource)
          .usersByUsernameQuery("select email, saltedPass as password, enabled "
          + "from users "
          + "where email = ?")
          .authoritiesByUsernameQuery("select email, authority from authorities where email = ?");
    }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
        //.antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/login").permitAll()
        .antMatchers("/submit_login").permitAll()
        .anyRequest().authenticated().and().formLogin().loginPage("/login").usernameParameter("email")
        .loginProcessingUrl("/submit_login")
        .defaultSuccessUrl("/", true)
        //.failureHandler(authenticationFailureHandler()).and().logout().logoutUrl("/perform_logout")
        //.deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())
        ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
