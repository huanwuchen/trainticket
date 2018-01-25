# android实现12306登录

------
大家都知道12306的验证码是很变态的。。。

## 1. 获取验证码

分析12306网页得出验证码接口地址是：https://kyfw.12306.cn/passport/captcha/captcha-check

![image](http://oaly5871x.bkt.clouddn.com/captcha-image.jpg)

每次请求这个接口验证码都是变化的


## 2. 验证验证码接口
### 2.1分析验证验证码接口
地址是：https://kyfw.12306.cn/passport/captcha/captcha-check
```java
 //参数如下
 HashMap<String, String> map = new HashMap<>();
 map.put("login_site", "E");//固定参数 不知道要干嘛的
 map.put("rand", "sjrand");//固定参数 不知道要干嘛的
 map.put("answer", strYan);//拼接出来的坐标数据
```
返回结果
```json
{"result_message":"验证码已经过期","result_code":"7"}
```
result_code  4：成功  其他：错误

### 2.2分析验证码
- [ ] 一共有八个验证码
```java
// -----------------------------------------------------------
//              |               |                |           
//      0       |       1       |        2       |      3
//              |               |                |
// -----------------------------------------------------------
//              |               |                |
//      4       |       5       |        6       |      7
//              |               |                |
// -----------------------------------------------------------
```
- [ ] 分析网页得出验证码坐标是
```java
// -----------------------------------------------------------
//              |               |                |           
//    35,35     |     105,35    |     175,35     |    245,35
//              |               |                |
// -----------------------------------------------------------
//              |               |                |
//    35,105    |    105,105    |    175,105     |   245,105
//              |               |                |
// -----------------------------------------------------------
```

## 3. 登录接口
地址是：https://kyfw.12306.cn/passport/web/login

```java
HashMap<String, String> map = new HashMap<>();
map.put("username", edit_name.getText().toString());//12306账号
map.put("password", edit_pwd.getText().toString());//12306密码
map.put("appid", "otn");//固定参数 不知道干嘛的
```

返回结果
```json
{"result_message":"登录成功","result_code":0,
"uamtk":"yH89oXpBLREqjUYKGp1z0nipeSHaXTkz8XwGMWwD1JbPabRbmk9190"}
```
result_code  0：成功  其他：错误

------

 到这里 登录流程走完 我在分析查询余票列表 请期待第二篇
 
 本文用到的代码在[github地址](https://github.com/huanwuchen/trainticket) 欢迎star
 
 有需要的朋友 请自行下载
 