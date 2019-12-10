# spring-boot-starter-validate-code

## 配置

```
spring:
  validate:
    code:
      image: #图形验证码相关配置
        length: 6 #验证码长度
        expireIn: 600 #验证码失效时间 单位为秒
        url: /merchant/merchant,/merchant/merchant #要监听的url 多个用逗号隔开
        width: 67 #生成的图片的宽度
        height: 23 #生成的图片的高度
      sms: #短信验证码相关配置
        length: 6 #验证码长度
        expireIn: 600 #验证码失效时间 单位为秒
        url: /merchant/merchant,/merchant/merchant #要监听的url 多个用逗号隔开
    
```

## 短信验证码的使用方法

假设有需求地址 /test/test1 需要短信验证码才可以访问，那么可以照如下步骤实现

1. 配置spring.validate.code.sms.url=/test/test1
2. GET /code/sms并在参数中带上手机号以及模板编号发送短信
3. 在header中带上手机号以及验证码访问/test/test1

例1(发送短信)：

```shell
curl -X GET \
  'http://127.0.0.1:8090/code/sms?mobile=13250974097&smsType=CHECK_CODE_TEMPLATE' 
```
例2(请求受验证码保护的资源)：
```shell
curl -X POST \
  http://127.0.0.1:8090/test/test1 \
  -H 'mobile: 13250974097' \
  -H 'smscode: 123456'
```

## 图形验证码的使用方法

假设有需求地址/test/test1 需要图片验证码才可以访问，那么可以照如下步骤实现

1. 配置 spring.validate.code.image.url=/test/test1
2. GET /code/image 并在参数中带上用户名
3. 在header中带上用户名以及验证码访问/test/test1

例1(发送短信)：

```shell
curl -X GET \
  'http://127.0.0.1:8090/code/image?username=aaaaa' 
```
例2(请求受验证码保护的资源)：
```shell
curl -X POST \
  http://127.0.0.1:8090/test/test1 \
  -H 'imagecode: 784512' \
  -H 'username: aaaaa'
```

注：验证码只能保护POST请求