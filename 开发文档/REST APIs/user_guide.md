## 概述

智家云硬盘 Rest API 是提供给开发者接入智家云硬盘开放平台的入口。
开发者可以通过智家云硬盘 API 进行二次开发。

## 变更记录

| 日期 | 版本 | 变更内容 |
| :------: | :------: | :------: | 
| 2021-03-11  | 0.0.1 | 初稿 |

## 准备工作

接入智家云硬盘服务前需要联系产品获取 AppKey

## API 网关

网关地址：https://zjdrive.cn/ 

## 签名验证

Rest API对每个访问请求进行身份验证，验证失败的请求无法调用API接口。

签名用的请求头参数

| 参数 | 类型 | 必选(默认值) | 描述 |
| :------ | :------ | :------ | :------ |
| X-NAS-APPID  | String | 是 | 平台分配的应用appkey |
| X-NAS-NONCE | String | 是 | 随机字符串（最大长度128个字符）,例如fdsfafewfd |
| X-NAS-TIMESTAMP | String| 是 | 当前 UNIX 时间戳，可记录发起 API 请求的时间。例如1594639036000，单位为毫秒。注意：如果与服务器时间相差超过1分钟，会引起签名过期错误。 |
| X-NAS-CLIENTTYPE  | String | 否(空字符串) | 客户端类型，ios: 51, aos: 50, web: 80, misc: 99999 |
| X-NAS-CLIENTVERSION  | String | 否(空字符串) | 客户端版本号 |
| X-NAS-DEVICEID  | String | 否(空字符串) | 设备ID，第三方APP维护的设备ID，可以选填 |
| X-NAS-VERSION  | String | 否(空字符串) | NAS接口版本号 |
| X-NAS-CHECKSUM | String | 是 | 签名串，具体算法参考 [签名代码](#sign_code) |

## 签名代码Demo

<span id="sign_code" />

```java
/**
 * 签名算法说明：
 * 按序拼接字符串 appId + timestamp + bodymd5 + nonce + clientType + clientVersion + deviceId + version + appSecret, 进行SHA256哈希计算，转化成16进制字符(String，小写)
 *
 * 注意：
 * 1. AppSecret为AppKey对应的秘钥
 * 2. apache commons-codecb参考版本1.13
 */
public class CheckSumBuilder {

    public static final String getCheckSum(String appId, Long timestamp, String bodymd5, String nonce, String appSecret) {
        return getCheckSum(appId, timestamp, bodymd5, nonce, "", "", "", "", appSecret);
    }

    public static final String getCheckSum(String appId, Long timestamp, String bodymd5, String nonce, String clientType, String clientVersion, String deviceId, String version, String appSecret) {
        String source = appId + timestamp + bodymd5 + nonce + clientType + clientVersion + deviceId + version;
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(source + appSecret);
    }

    /**
     * 计算并获取md5值(小写)
     */
    public static final String getMD5(String requestBody) {
        String bodymd5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(requestBody);
        return bodymd5;
    }

    public static final void main(String[] args) {
        String sampleRequestBody = "{}";
        String bodymd5 = getMD5(sampleRequestBody);
        String checkSum = getCheckSum("demo", System.currentTimeMillis(), bodymd5, "dkfafkdjfk", "demosecret");
        System.out.println(checkSum);
    }
}
```


## 公共响应参数
|参数|类型|必须|说明|
|:----|:----|:----|:----|
|data|json|N|内容|
|code|int|Y|错误码(200成功，非200不成功)|
|msg|String|N|描述|

```json
{
  "data": {},
  "code": 200,
  "msg": "OK"
}
```
## 错误码

```java
public enum Status {
    OK(200, "OK"), // 成功

    BAD_REQUEST(400, "Bad Request"), // 错误请求
    UNAUTHORIZED(401, "Unauthorized"), // 没有验证
    FORBIDDEN(403, "Forbidden"), // 禁止
    NOT_FOUND(404, "Not Found"), // 消失
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"), // 不允许
    NOT_ACCEPTABLE(406, "Not Acceptable"), // 不接受
    GONE(410, "Gone"), // 已迁移
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"), // 不支持的媒体
    EXPECTATION_FAILED(417, "Expectation Failed"), // 预期失败
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"), // 内部服务错误

    UNKNOWN(900, "Unknown"), // 未知错误
    UNAUTHENTICATED(901, "Unauthenticated"), // 未经授权
    PERMISSION_DENIED(902, "Permission Denied"), // 没有许可
    INVALID_ARGUMENT(903, "Invalid Argument"), // 非法参数
    DATABASE_EXCEPTION(904, "Database Exception"), //数据库异常
    CANCELLED(905, "Cancelled"), // 取消
    FREQUENTLY(906, "Frequently"), // 频繁
    NOT_EXIST(907, "Not Exist"), // 不存在
    EXIST(908, "Exist"), // 存在
    NOT_READY(909, "Not Ready"), // 未就绪
    READY(910, "Ready"), // 就绪
    NOT_EXPIRED(911, "Not Expired"), // 没有过期
    EXPIRED(912, "Expired"), // 过期
    NOT_DONE(913, "Not Done"), // 未完成
    DONE(914, "Done"), // 已完成
    NOT_ENOUGH(915, "Not Enough"), // 不足
    ENOUGH(916, "Enough"), // 足够
    NOT_EXCESS(917, "Not Excess"), // 没有过量
    EXCESS(918, "Excess"), // 过量
    NOT_DUPLICATE(919, "Not Duplicate"), // 不重复
    DUPLICATE(920, "Duplicate"), // 重复
    NOT_BUSY(921, "Not Busy"), // 不忙
    BUSY(922, "Busy"), // 忙
    UNAVAILABLE(923, "Unavailable"), // 不可用
    AVAILABLE(924, "Available"), // 可用
    UNIMPLEMENTED(925, "Unimplemented"), // 未实现
    IMPLEMENTED(926, "Implemented"), // 实现
    BANNED(927, "BANNED"), // 受限
    SPAMMED(928, "SPAMMED"), // 被识别为垃圾
    CLIENT_VERSION_NOT_SUPPORTED(929, "Client Version not supported") // 客户端版本不支持
}
```


## Rest APIs

### 获取Access Token

```
