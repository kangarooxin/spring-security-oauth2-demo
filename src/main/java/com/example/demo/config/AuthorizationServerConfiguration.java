package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.concurrent.TimeUnit;

/**
 * @author kangarooxin
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * *用来配置令牌端点(Token Endpoint)的安全约束。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()//默认/oauth/token认证是使用BasicAuth，此处设置允许通过表单URL提交client_id和client_secret。
                .passwordEncoder(passwordEncoder)//设置密码编码器， 使用client_id登录时，密码加密要跟用户加密一致。
                .checkTokenAccess("isAuthenticated()")//开启/oauth/check_token验证端口认证权限访问
        ;
    }

    /**
     * 配置OAuth2的客户端相关信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauth2ClientDetailsService());
    }

    /**
     * 配置授权服务器端点的属性
     *
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(oauth2TokenStore())//指定token存储服务
                .authorizationCodeServices(authorizationCodeServices())//指定code生成服务
                .userDetailsService(userDetailsService)//用户服务
                .reuseRefreshTokens(false)//每次刷新token会创建新的refresh_token
        ;
    }

    /**
     * 设置令牌存储方式
     * InMemoryTokenStore 在内存中存储令牌。
     * RedisTokenStore 在Redis缓存中存储令牌。
     * JwkTokenStore 支持使用JSON Web Key (JWK)验证JSON Web令牌(JwT)的子Web签名(JWS)
     * JwtTokenStore 不是真正的存储，不持久化数据，身份和访问令牌可以相互转换。
     * JdbcTokenStore 在数据库存储，需要创建相应的表存储数据
     */
    @Bean
    public TokenStore oauth2TokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * 设置Code生成服务
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * client服务
     *
     * @return
     * @throws Exception
     */
    @Bean
    public ClientDetailsService oauth2ClientDetailsService() throws Exception {
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        builder.withClient("clientId").secret(passwordEncoder.encode("clientSecret"))
                .scopes("scope1", "scope2")
                .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
                .redirectUris("http://www.baidu.com")
                .autoApprove(false)
                .accessTokenValiditySeconds((int)TimeUnit.HOURS.toSeconds(12))
                .refreshTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30))
        ;
        return builder.build();
    }
}
