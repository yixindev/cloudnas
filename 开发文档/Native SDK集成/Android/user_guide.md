## 易信云Nas-SDK 安卓用户手册

[TOC]

### 1.项目介绍

* 概述：本文档指导客户端开发人员，用于快速集成云Nas-SDK，使app具备基于SMB协议的远程文件的快速访问/上传/下载/预览等能力；
* 本SDK的主要能力是基于flutter技术实现；
* 远程资源基于本地stream转流进行了封装实现，可以适配绝大多数的音视频播放器；

### 2.接入准备

#### 2.1 环境要求
* android-minsdk >= 17
* app额外权限申请： sdk通过gradle方式导入，无需额外进行配置
* android.useAndroidX=true
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
            url "http://nexus.yixin.im/repository/maven-public/"
            credentials {
                username 'yixinnas'
                password '****' //联系对应的开发人员进行提供
            }
        }
    }

    //配置snapshot版本时需要添加，release版本不需要
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
    }
}
```


> 在项目gradle.properties下增加如下配置
```
android.jetifier.blacklist=bcprov-jdk15on
```

* 集成方可选择以下其中一种方式添加云nas依赖库
    * flutter插件方式：对于集成方已经接入flutter框架，云nas仅以插件形式提供集成
    * 原生sdk方式：集成方未接入flutter框架，云nas以flutterFramework+sdk形式集成

##### 2.2.1 flutter插件方式
> pubsepc.yaml配置flutter插件远程依赖
```
dependencies:
    cloud_nas:
        git:
            url: https://github.com/yixindev/cloud_nas_plugin

```
> app/build.gradle配置sdk依赖
```
defaultConfig {
    multiDexEnabled true
    ndk {
        //集成方依自身项目实际情况选择cpu架构
        abiFilters 'armeabi-v7a'//, 'arm64-v8a', 'x86_64'
    }
}

dependencies {
    implementation 'im.yixin.nas.plugin:nasFlutterSDK:1.0.9-SNAPSHOT'
}
```

##### 2.2.2 原生sdk方式

> 项目app目录下的build.gradle，添加sdk依赖
```
defaultConfig {
    multiDexEnabled true
    ndk {
        //集成方依自身项目实际情况选择cpu架构
        abiFilters 'armeabi-v7a'//, 'arm64-v8a', 'x86_64'
    }
}

dependencies{
    implementation "im.yixin.nas.plugin:flutter:1.0.9-SNAPSHOT"
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
        //初始化成功之后调用YXNasSDK.authLogin接口
    }
})

```

#### 3.2 ~~添加token刷新监听器（暂时废弃）~~

* 参数列表
    * listener: 类型interface | 建议必传 | 请求刷新token的监听器；
* 接口调用说明：
    * 前置条件：sdk.init调用
    * 当底层token过期时，通过监听器回调给主app，主app需刷新token，并将结果通过`MethodCall`回传给sdk；

> 调用示例
```java
//建议在应用启动时添加token监听器
YXNasSDK.instance.init()

//需要提前执行init方法
YXNasSDK.instance.setTokenRequestListener(object : ITokenRequestListener {

    //ps: 此方法通过主线程回调
    override fun onTokenRequest(methodCall: ITokenRefreshCall?) {
        //主app异步获取userToken，将结果返回sdk, 成功调用succes返回token数据，失败调用error方法返回message
        if(success){
            val token: String = "${access_token}";
            methodCall.success(token)
        }else{
            methodCall.error(message);
        }
    }
}
```

#### 3.3 获取Fragment容器

* 接口调用说明：如主app以Fragment方式引入sdk界面，需通过本接口实例化Fragment
* 返回的fragment类型为**androidx.fragment.app**

> 调用示例
```kotlin
val nasFragment = YXNasSDK.instance.obtainFlutterHost()
```

##### 3.3.1 通过Intent启动云Nas

* 以非Fragment方式，单独拉起Flutter页面，适用于应用内点击banner/小程序/子功能模块 等场景

> 调用示例
```
startActivity(YXNasSDK.instance.obtainFlutterIntent())
```

#### 3.4 授权登录

* 参数列表
    * token: 类型string | 必传 | appToken，用于sdk内部交换sdkToken
    * authType: 类型enum | 授权类型
        * TypeUniversal：用于翼之家等app集成时传参
        * TypeXiaoYi：小易管家类型
    * callback: 类型interface | 建议必传 | 用户授权登录回调，当云nas授权结束后将结果回调给主app
* 接口调用说明:
    * 前置条件: SDK初始化成功，即初始化回调中 `code==200`
    * 在SDK.init初始化成功之后调用

> 调用示例
```java
YXNasSDK.instance.authLogin(token, NasAuthType.TypeXiaoYi, object : INasInvokeCallback<Void> {
    override fun onResult(code: Int, message: String?, data: Void?) {
        //通过code判断SDK用户授权结果: 200即为成功
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

#### 3.6 视频播放URL监听器接口
* 参数列表
    * listener: 类型interface | 建议必传 | 视频url播放回调监听器
        * videoURL: 类型string | 非空 | 视频url，本地视频格式file://${文件绝对路径}；线上http(s)://xx
        * videoName: 类型string | 视频名称
        * methodCall: 结果回调
* 接口调用说明：
    * 前置条件：sdk.init调用
    * 底层回传视频URL和视频名称给集成方，由集成方自行进行播放，成功通过success回传结果，失败通过error返回错误码及提示信息

> 调用示例
```
YXNasSDK.instance.setVideoPlayListener(object : IVideoPlayListener {
    override fun onVideoPlayCallback(
        videoURL: String,
        videoName: String?,
        methodCall: IVideoPlayCall?
    ) {
        //视频播放能力由集成方自行实现
        ...
        //通过methodCall回调播放结果
        if (videoURL.isNotEmpty()) {
            methodCall?.success()
        } else {
            methodCall?.error("xxx") //错误信息
        }
    }

})
```

#### 3.7 天翼云盘TokenCode请求接口
* 参数列表
    * listener: 类型interface | 建议必传 | token刷新监听器
        * methodCall: 结果回调
* 接口调用说明：
    * 前置条件：sdk.init调用
    * 通过success向sdk传递天翼云盘TokenCode，通过error返回错误码及提示信息

> 调用示例
```
YXNasSDK.instance.setTokenCodeRequestListener(object : ITokenRequestListener {
    override fun onTokenRequest(methodCall: ITokenRefreshCall?) {
        //获取tokenCode
        methodCall?.success("${tokenCode}")
        //未获取可以回传错误信息 如：methodCall?.error("tokenCode为空")
    }
})
```


### 4.统一错误码对照表

|错误码|描述 |
| :-- | :-- |
| 200 | 调用成功 |
| 4000 | 请求参数错误 |
| 4001 | 用户已登录，无需重复登录 |
| ~~4002~~ | ~~用户已退出，无需重复退出~~ |
| 4003 | 用户退出失败 |
| 4004 | 方法未实现 |
| 4005 | 返回数据解析异常 |
| 4006 | 视频播放失败， 用于sdk内部回调 |
| 4007 | 刷新token失败， 用于sdk内部回调 |


### 5.release混淆配置

```xml


#flutter 基础混淆配置
-keep class io.flutter.app.** {*;}
-keep class io.flutter.plugin.** {*;}
-keep class io.flutter.util.** {*;}
-keep class io.flutter.view.** {*;}
-keep class io.flutter.** {*;}
-keep class io.flutter.plugins.** {*;}

## fijkplayer插件
-keep class tv.danmaku.ijk.media.player.** {*;}

## x5浏览器
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.** {*;}
-keep class com.tencent.tbs.** {*;}

-keep class im.yixin.**{*;}
-keep class com.mr.flutter.plugin.filepicker.**{*;}

-keep class com.baseflow.permissionhandler.**{*;}

-keep class com.hierynomus.**{*;}
-keep class org.slf4j.**{*;}
-keep class net.engio.**{*;}
-keep class com.yanzhenjie.andserver.**{*;}

```


### 6.附录

#### 6.1 关于Demo
* 请联系SDK开发人员提供demo.apk
* 或者前往 [云NasSDK Git库地址](https://github.com/yixindev/cloudnas/tree/main/SampleCode/NasSDKDemo_Android)自行编译打包
<!--* 下载地址: https://www.pgyer.com/kNQy-->
<!--* 二维码扫码地址: <br>-->
<!--![](./images/android_download_QRCode.png)-->

### 7.版本更新记录

| 版本名称 | 更新说明 | 日期 |
| :-- | :-- | :-- |
| 1.0.2 | 1.支持小易管家接入<br> 2.授权接口入参变更，废弃token刷新接口 | 2021/6/25 |
| 1.0.3 | 1.修复重命名文件导致的页面变空白问题 <br>2.修复文件下载引发的app卡死问题| 2021/8/2 |
| 1.0.4 | 1.支持云宽带环境下切换smb协议完成文件上传/下载 <br>2.增加图片本地缩略图功能 <br>3.增加相册自动备份功能 <br>4.增加清除缓存功能| 2021/8/27 |
| 1.0.5 | -- | 2021/10/15 |
| 1.0.6 | 支持64位so库动态加载 | 2021/11/15 |
| 1.0.7 | 1.增加天翼云盘转存功能 | 2021/11/30 |
| 1.0.8 | 1.支持32/64打包<br>2.支持上海专区配置动态下发 | 2022/1/12 |
| 1.0.9 | 1.增加智能相册功能（上海专区）<br>2.增加停机状态下判断 <br>3.fluttersdk更新至2.5.0 <br>**4.提供以flutter插件方式集成进目标app** | 2022/4/29 <br/>**2022/8/3** |