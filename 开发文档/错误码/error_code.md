## 错误码
### 服务器

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
### SDK
```java
```
