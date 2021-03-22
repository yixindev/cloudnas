## 易信云Nas-SDK 安卓用户手册

[TOC]

### 1.项目介绍

* 概述：本文档指导客户端开发人员，用于快速集成云Nas-SDK，使app具备基于SMB协议的远程文件的快速访问/上传/下载/预览等能力；
* 本SDK的主要能力是基于flutter技术实现；

### 2.接入准备

#### 2.1 环境要求
* android-minsdk >= 17
* app额外权限申请： sdk通过gradle方式导入，无需额外进行配置

> manifest配置
```xml
略；
```

#### 2.2 依赖库导入

> 修改位于项目根目录下的build.gradle
```xml
allprojects {
    repositories {
        maven { url 'http://download.flutter.io' }
        maven {
            url 'https://dl.bintray.com/sbinder/sqlite3-native-library/'
        }
        maven {
            url "http://nexus.yixin.im/repository/maven-public/"
            credentials {
                username 'yixinnas'
                password 'nas123'
            }
        }       
    }
    
    //配置snapshot版本时需要添加，release版本不需要
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
    }
}
```

> 项目app目录下的build.gradle，添加sdk依赖
```xml
dependencies{
    implementation "im.yixin.nas:nasFlutterSDK:1.0.0-SNAPSHOT"    
}
```

### 3.API列表

* api具体的调用时机及方式可参考同Git项目下的安卓Demo工程；

#### 3.1 SDK初始化

* 参数列表
    *  app: 类型Application | 必传 | 应用上下文；
    *  appkey: 类型string | 必传 | app唯一标识，可通过后台申请；
    *  callback: 类型interface, sdk初始化回调；
        * code: 类型int | 错误码
        * message: 类型string | 错误码描述
* 接口调用说明：应用启动时，在`application.onCreate`方法中调用，通过callback中的code值是否等于`200`判断是否初始化成功。

> 调用示例
```java
val appkey = 'xxx'

YXNasSDK.instance.init(this, appkey, object : INasInvokeCallback<Void> {

    override fun onResult(code: Int, message: String?, data: Void?) {
        //通过 code==200 判断sdk是否初始化成功
    }
})

```

#### 3.2 添加token刷新监听器

* 参数列表
    * listener: 类型interface | 建议必传 | 请求刷新token的监听器；
* 接口调用说明：当底层token过期时，通过监听器回调给主app，主app需刷新token，并将结果通过`MethodCall`回传给sdk；

> 调用示例
```java
//建议在应用启动时添加token监听器

YXNasSDK.instance.init()

//需要提前执行init方法
YXNasSDK.instance.setTokenRequestListener(object : ITokenRequestListener {

    //ps: 此方法通过主线程回调
    override fun onTokenRequest(methodCall: IMethodCall<String>?) {
        //主app异步获取userToken，将结果返回sdk, 成功调用succes返回token数据，失败调用error方法返回code + message
        if(success){
            val token: String = "${access_token}";
            methodCall.success(token)
        }else{
            methodCall.error(code, message);
        }
    }
}
```

#### 3.3 获取Fragment容器

* 接口调用说明：如主app以Fragment方式引入sdk界面，需通过本接口实例化Fragment

> 调用示例
```kotlin
val nasFragment = YXNasSDK.instance.obtainFlutterHost()
```

#### 3.4 授权登录

* 参数列表
    * mobile: 类型string | 必传 | 用户手机号
    * callback: 类型interface | 建议必传 | 用户授权登录回调，当云nas授权结束后将结果回调给主app
* 接口调用说明: 
    * 前置条件: SDK初始化成功，即初始化回调中code==200
    * 主app获取到sdk的用户token数据之后，再调用此接口

> 调用示例
```java
YXNasSDK.instance.authLogin(mobile, object : INasInvokeCallback<Void> {
    override fun onResult(code: Int, message: String?, data: Void?) {
        //通过code判断SDK用户授权结果
    }
}
```

#### 3.5 用户退出登录
* 参数列表
    * callback: 类型interface | 建议必传 | 用户退出回调，当云nas退出登录后将结果回调给主app
* 接口调用说明:
    * 前置条件: SDK初始化成功，即初始化接口的回调中code==200
    * 主app退出登录之后，调用此接口退出sdk，并清除SDK底层用户数据

> 调用示例
```java
YXNasSDK.instance.logout(object : INasInvokeCallback<Void> {
    override fun onResult(code: Int, message: String?, data: Void?) {
        //通过code判断SDK用户登出结果
    }
}
```

### 4.统一错误码对照表

|错误码|描述 |
| :-- | :-- |
| 200 | 调用成功 |
| 400 | 请求参数错误 |
| 4002 | 数据解析异常 |
| 4003 | 刷新token失败， 用户数据为空或未登录 |
| 4004 | 获取token失败 |

### 5.release混淆配置

```xml
-keep im.yixin.nas.sdk.**{*;}
```


### 6.附录
#### 6.1 环境配置
| api环境 | base-url地址 |
| :-- | :-- |
| 测试环境 | http://test.yixin.im |
| 生产环境 | https://zjdrive.cn|

