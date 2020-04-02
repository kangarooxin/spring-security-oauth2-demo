package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    //{"access_token":"81b2b81e-7a4a-4e8f-ad9c-81d3718528c1","token_type":"bearer","refresh_token":"bc09d19f-0777-44d8-acb7-8c809e99aa0f","expires_in":43199,"scope":"scope1 scope2"}
    @Test
    public void token_password() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "user1");
        params.add("password", "123456");
        String response = restTemplate.withBasicAuth("clientId", "clientSecret").
                postForObject("/oauth/token", params, String.class);
        System.out.println(response);
    }

    //{"access_token":"b164bb4a-f0cf-4029-834c-6063ad0458f6","token_type":"bearer","expires_in":43199,"scope":"scope1 scope2"}
    @Test
    public void token_client() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        String response = restTemplate.withBasicAuth("clientId", "clientSecret").
                postForObject("/oauth/token", params, String.class);
        System.out.println(response);
    }

    @Test
    public void token_code() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", "1H7K2q");
        String response = restTemplate.withBasicAuth("clientId", "clientSecret")
                .postForObject("/oauth/token", params, String.class);
        System.out.println(response);
    }
}
