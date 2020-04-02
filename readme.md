# Getting Started

#### 支持4种授权方式：

* 授权码（authorization_code）
* 密码式（password）
* 客户端凭证（client_credentials）
* 隐藏式（implicit）

#### 客户端凭证（client_credentials）

一般用于资源服务器是应用的一个后端模块，客户端向认证服务器验证身份来获取令牌。

```
curl -d "grant_type=client_credentials&client_id=clientId&client_secret=clientSecret" http://localhost:8080/oauth/token
{
  "access_token" : "6c246e41-7d9a-4b69-b340-893635638ed8",
  "token_type" : "bearer",
  "expires_in" : 42444,
  "scope" : "scope1 scope2"
}
```

#### 密码式（password）

使用用户名/密码作为授权方式从授权服务器上获取令牌，一般不支持刷新令牌。

```
curl -d "grant_type=password&username=user1&password=123456&client_id=clientId&client_secret=clientSecret" http://localhost:8080/oauth/token
{
  "access_token" : "b7ca73bc-992c-4c23-abbd-f819aa220199",
  "token_type" : "bearer",
  "refresh_token" : "51661039-fb67-4e6b-a563-bd4c58b084c0",
  "expires_in" : 43199,
  "scope" : "scope1 scope2"
}
```

#### 隐藏式（implicit）

和授权码模式类似，只不过少了获取code的步骤，是直接获取令牌token的，适用于公开的浏览器单页应用，令牌直接从授权服务器返回，不支持刷新令牌，且没有code安全保证，令牌容易因为被拦截窃听而泄露。
   访问：
   ```
   http://127.0.0.1:8080/oauth/authorize?client_id=clientId&response_type=token&scope=scope1%20scope2&redirect_uri=http://www.baidu.com
   ```
   登录成功，回调地址携带access_token：
   ```
   http://www.baidu.com/#access_token=551b968a-8e1f-4f4e-bc5e-e94544d982ec&token_type=bearer&expires_in=43199
   ```

#### 授权码（authorization_code）

授权码模式（authorization
code）是功能最完整、流程最严密的授权模式，code保证了token的安全性，即使code被拦截，由于没有app_secret，也是无法通过code获得token的。
   访问：
   ```
   http://127.0.0.1:8080/oauth/authorize?client_id=clientId&response_type=code&scope=scope1%20scope2&redirect_uri=http://www.baidu.com
   ```
   登录成功，回调地址携带code：
   ```
   http://www.baidu.com/?code=s6htPB
   ```
3. 使用code换取access_token

   ```
   curl -d "grant_type=authorization_code&code=s6htPB&redirect_uri=http://www.baidu.com&client_id=clientId&client_secret=clientSecret" http://127.0.0.1:8080/oauth/token
   {
     "access_token": "551b968a-8e1f-4f4e-bc5e-e94544d982ec",
     "token_type": "bearer", 
     "refresh_token": "248dff03-4124-4867-a3c9-7e299d2283c8", 
     "expires_in": 42759, 
     "scope":"scope1 scope2" 
   }  
   ```
4. 刷新token

   ```
   curl -d "grant_type=refresh_token&refresh_token=248dff03-4124-4867-a3c9-7e299d2283c8&client_id=clientId&client_secret=clientSecret" http://127.0.0.1:8080/oauth/token
   {
      "access_token" : "39a2536a-d420-4798-abb3-1fc083cce887",
      "token_type" : "bearer",
      "refresh_token" : "7ba0dfcd-433e-4e5e-b06c-97c61bd54a9a",
      "expires_in" : 43199,
      "scope" : "scope1 scope2"
   }
   ```

