## 概述

智家云硬盘iOS SDK提供了一套简单易用的接口，允许开发者通过调用cloudNas SDK(以下简称SDK)提供的API，快速地集成存储功能至现有iOS应用中。

## 变更记录

| 日期 | 版本 | 变更内容 |
| :------: | :------: | :------- |
| 2021-03-11  | 1.0.0 | 首次正式发布 |

## 快速接入

#### 开发环境准备

| 名称 | 要求 |
| :------ | :------ |
| iOS版本 | 10.0以上 |
| CPU架构支持 | ARM64|
| IDE | XCode |
| 其他 | CocoaPods |

#### SDK快速接入

1. 新建iOS工程

    a. 运行XCode，选择Create a new Xcode project，选择Single View App，选择Next。
    ![new android project](images/new_project.png)
    
    b. 配置工程相关信息，选择Next。
    
    c. 然后选择合适的工程本地路径，选择Create完成工程创建。

2. 通过CocoaPods集成SDK

    进入到工程路径执行pod命令，生成Podfile文件，注意CocoaPods版本使用1.9.1以上的，防止因为版本过低导致无法拉取sdk。
    ```groovy
    pod init
    ```
    打开Podfile文件添加如下代码，保存。

    ```
    pod 'cloudNasSDK'
    ```

    执行pod命令，安装SDK

    ```
    pod install
    ```
    
2. SDK初始化

    在使用SDK其他功能之前首先需要完成SDK初始化，初始化操作建议在**AppDelegate.m**的**application:didFinishLaunchingWithOptions:**方法执行。代码示例如下：
    ```objective-c
   ```

    **注意：其他操作一定更要等到初始化接口的回调返回之后再执行，否则会失败。**

6. 调用相关接口完成特定功能，详情请参考API文档。

- 登录鉴权
    ```objective-c
    ```
    
- 注销登录
    ```objective-c
    ```

## 业务开发

### 初始化

#### 描述

在使用SDK其他接口之前，首先需要完成初始化操作。

#### 业务流程

1. 配置初始化相关参数

```objective-c
```

2. 调用接口并进行回调处理，该接口无额外回调结果数据

```objective-c
```

#### 注意事项

- 其他操作一定更要等到初始化接口的回调返回之后再执行，否则会失败

--------------------

### 登录鉴权

#### 描述

请求SDK进行登录鉴权，只有完成SDK登录鉴权才允许后续操作。

#### 业务流程

1. 获取登录用Account和Token。Token由应用服务器下发，但SDK不提供对应接口获取该信息，需要开发者自己实现。

```objective-c
NSString *accountId = @"mobile";
NSString *accountToken = @"accountToken";
```

2. 登录并进行回调处理，该接口无额外回调结果数据

```objective-c
```

#### 注意事项
app切换账号或者登出时必须调用注销接口

--------------------

