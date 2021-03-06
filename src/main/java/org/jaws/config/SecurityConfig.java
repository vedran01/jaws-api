package org.jaws.config;

import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean("passayValidator")
  public PasswordValidator passwordValidator(@Value("classpath:passay.properties") Resource resource) {
    LengthRule r1 = new LengthRule(6,15);
    WhitespaceRule r2 = new WhitespaceRule();
    CharacterCharacteristicsRule r3 = new CharacterCharacteristicsRule();
    r3.getRules().add(new CharacterRule(EnglishCharacterData.UpperCase, 2));
    r3.getRules().add(new CharacterRule(EnglishCharacterData.Digit, 2));
    IllegalSequenceRule r4 = new IllegalSequenceRule(EnglishSequenceData.Numerical, 4, true);

    PasswordValidator passwordValidator;

    try {
      Properties properties = PropertiesLoaderUtils.loadProperties(resource);
      MessageResolver resolver = new PropertiesMessageResolver(properties);
      passwordValidator = new PasswordValidator(resolver, r1, r2, r3, r4);
    } catch (IOException e) {
      log.warn("No passay properties find");
      passwordValidator = new PasswordValidator(r1, r2, r3, r4);
    }

    return passwordValidator;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // TODO set with custom user details
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // DISABLED SECURITY
    http
        .csrf().disable()
        .authorizeRequests()
        .anyRequest()
        .permitAll();
  }
}
