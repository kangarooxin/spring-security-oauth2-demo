package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author kangarooxin
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置Security
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.requestMatchers().antMatchers("/oauth/**", "/login", "/logout")//配置需要Security拦截的请求
                .and().formLogin().permitAll().loginPage("/login")//配置登录页面
                .and().logout().logoutUrl("/logout")//配置登出页面
                .and().authorizeRequests().anyRequest().authenticated()//其它页面都需要鉴权认证
                .and().csrf().disable()//不开启csrf
        ;
    }

    /**
     * 用户服务
     *
     * 此处在内存创建了2个用户，实际场景替换为db服务查询，只需要实现UserDetailsService即可
     *
     * @return
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user1").password(passwordEncoder.encode("123456")).authorities("USER").build());
        manager.createUser(User.withUsername("user2").password(passwordEncoder.encode("123456")).authorities("USER").build());
        return manager;
    }

    /**
     * 设置密码校验器
     * NoOpPasswordEncoder 直接文本比较  equals
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
