## 概述

智家云硬盘iOS SDK提供了一套简单易用的接口， 允许开发者通过调用NAS SDK(以下简称SDK)提供的API，快速地集成智家云硬盘的界面和功能至现有的iOS应用中。

## 变更记录

| 日期 | 版本 | 变更记录 |
| :------: | :------: | :------ |
| 2021-03-22 | 1.0.2 | 正式发布 |
| 2021-06-24 | 1.0.3 | 1.支持视频转流播放 2.支持小翼管家接入 3.问题修复 |
| 2021-06-25 | 1.0.4 | 优化业务逻辑 | 
| 2021-06-29 | 1.1.0 | 1.去掉多余权限申请 2.缺陷修复 |
| 2021-06-30 | 1.1.1 | 修复音视频播放问题 |
| 2021-07-09 | 1.1.2 | 解决了swift标准库引入问题 |
| 2021-08-26 | 1.1.3 | 1.新增相册自动备份 2.新增smb协议支持 3.新增网络自动切换 4.新增文件缩略图和缓存清理 | 
| 2021-08-31 | 1.1.4 | 去掉FLEX依赖 |
| 2021-10-15 | 1.1.5 | 1.修改验证码限制 2.配合运营推广 3.问题修复 |
| 2021-10-21 | 1.1.7 | 修复激活流程中的问题 |
| 2021-11-30 | 1.2.0 | 新增转存功能 |
| 2022-01-12 | 1.2.1 | 1.增加区域动态配置  2.缺陷修复 |
| 2022-03-07 | 1.2.2 | 修复部分缺陷 |
| 2022-05-05 | 1.2.3 | 1.新增智能相册功能 2.开发框架升级 3.性能优化 |
| 2022-05-12 | 1.2.4 | 1.优化文件选择功能 2.缺陷修复 |
| 2022-08-16 | 1.2.6 | 1.新增文件移动功能 2.修复大文件上传和自动备份中的问题 3.完善日志打印模块 4.支持用户反馈 5.支持flutter plugin插件化 |
| 2022-08-23 | 1.2.7 | 优化日志输出 |
| 2022-09-01 | 1.2.8 | 1.新增授权类型：云宽带 2.返回授权结果 3.新增错误码：用户不存在 |
| 2022-11-04 | 1.2.9 | 1.新增微信备份功能 2.缺陷修复 |
| 2023-01-06 | 1.3.0 | 1.新增印鸽打印功能 2.音频播放支持拖拽 3.缺陷修复 |
| 2023-01-10 | 1.3.1 | 1.新增跳转智家硬盘小程序 2.缺陷修复 |
| 2023-02-08 | 1.3.4 | 1.flutter版本升级到3.0.0 2.云冲印地址动态配置 |
| 2023-02-10 | 1.3.6 | 1.修复云冲印本地图片上传问题 2.修复印鸽微信支付跳转失败的问题 |
| 2023-02-15 | 1.3.7 | 新增备份功能引导 |
| 2023-03-20 | 1.3.8 | 1.新增主子账号功能 2.修复云网关下文件预览问题 3.修复文件选择页面导航栏偏移和透明的问题|
| 2023-03-22 | 1.3.9 | 1.关闭日志工具 2.优化日志打印 |
| 2023-03-27 | 1.3.10 | 修复扩容无法识别设备的问题 |
| 2023-04-13 | 1.3.12 | 1.新增云回看功能 2.修复缺陷 |
| 2023-04-23 | 1.4.0 | 1.首页改版 2.功能优化 3.修复缺陷 |
| 2023-05-11 | 1.4.2 | 1.新增公共空间功能 2.新增子账号容量配置功能 |
| 2023-05-12 | 1.4.3 | 修复子账号为空异常 |
| 2023-06-13 | 1.4.7 | 新增接口支持外部传入文件上传 |

## 快速接入

#### 开发环境准备

| 名称 | 要求 |
| :------ | :------ |
| iOS版本 | 10.0以上 |
| CPU架构支持 | ARM64 |
| IDE | XCode |
| 其他 | CocoaPods |

#### 注意事项

- 由于SDK底层使用Flutter跨平台进行开发，目前不支持x86_64架构的AOT包导出，所以SDK无法在模拟器上编译运行。

#### SDK快速接入

1. 新建iOS工程

    a. 运行XCode，选择Create a new Xcode project，选择Single View App，选择Next。
    
    ![new android project](images/new_project.png)
    
    b. 配置工程相关信息，选择Next。
    
    c. 然后选择合适的工程本地路径，选择Create完成工程创建。

2. 通过CocoaPods集成SDK
	
    进入到工程路径执行pod初始化命令```pod init``` ，生成Podfile文件，注意CocoaPods版本使用1.9.1以上的，防止因为版本过低导致无法拉取sdk。
    
    打开Podfile文件添加如下代码，保存。

    ```
    pod 'NASSDK'
    ```

    执行pod命令，安装SDK

    ```
    pod install
    ```
    
3. 权限配置

	打开项目目录下的xcworkspace工程文件，点击打开```Info.plist```文件，在其中增加相册相关的访问权限配置：
	
	```
	<key>NSPhotoLibraryAddUsageDescription</key>
	<string>需要您的允许才能保存图片到相册</string>
	<key>NSPhotoLibraryUsageDescription</key>
	<string>需要您的允许才能访问相册</string>
	```
	
## 接口开发

### 1 引入头文件

```
#import <NASSDK/NASSDKNative.h>
```

### 2 获取界面容器

#### 2.1 接口描述

获取SDK的业务界面，返回值是UIViewController类的实例，将获取的对象添加到相应的位置，例如：TabbarController。

#### 2.2 接口定义

```
+(UIViewController*)contentViewController;
```

#### 2.3 注意事项

- 容器内部的导航栏样式和跳转由SDK内部定制，不跟随App的全局样式。

--------------------

### 3 初始化

#### 3.1 描述

在调用SDK其他接口之前，首先需要传入应用的**appKey**完成SDK的初始化。

#### 3.2 接口定义

```
+(void)initializeWithAppKey:(NSString*)appKey
                 completion:(NASCompletionBlock)completion
```

#### 3.3 调用示例

```
[NASSDKNative initializeWithAppKey:appKey
                        completion:^(NSInteger resultCode, NSString *resultMsg) {
    if (resultCode == NAS_RESULT_SUCCESS) {
        //初始化成功
    }
    else{
        //初始化失败
    }
}];
```

#### 3.4 注意事项

- 其他操作必须要等到初始化接口的回调执行之后再进行，否则会失败

--------------------

### 4 设置授权令牌

#### 4.1 接口描述

设置SDK登录授权所必须的token、授权类型以及登录结果回调。

目前小翼管家接入授权类型为**NASAuthTypeXiaoYi**，其他接入方授权类型为**NASAuthTypeUniversal**。

#### 4.2 接口定义

```
+(void)setAuthToken:(NSString*)token
               type:(NASAuthType)type
         completion:(NASCompletionBlock)completion;
```

#### 4.3 调用示例

```
[NASSDKNative setAuthToken:self.token
							type:NASAuthTypeXiaoYi 
					completion:^(NSInteger resultCode, NSString *resultMsg) {
   //SDK登录成功
   if (resultCode == NAS_RESULT_SUCCESS) {
   }
   //SDK登录失败
   else{
   }
}];
```

#### 4.4 注意事项
- 根据接入方授权流程的不同，授权类型请确保输入正确。

#### 4.5 错误码

关于授权错误码的特别说明，错误码只存在200/4000/4001/9000四种类型的错误码，分别对应以下几种场景

| 错误码 | 对应场景 |
| :------: | :------ |
| 200 | 授权成功，用户已开通云nas服务，包含已开通已激活/已开通未激活/移机中状态 |
| 4000 | 授权失败，请求参数错误，如token传空 |
| 4001 | 授权失败，用户未开通云nas服务 |
| 9000 | 授权失败，网络未联通/请求超时/服务器异常/停机状态 |

--------------------

### 5 注销

#### 5.1 接口描述

请求SDK注销自己当前的登录账号，变成未登录状态。

#### 5.2 接口定义

```
+(void)logoutWithCompletion:(NASCompletionBlock)completion;
```

--------------------

### 6 监听视频播放请求
#### 6.1 接口描述
当SDK点击视频文件进行在线播放时，会调用传入的回调函数，并将处理后的视频地址作为参数传入，应用获取到视频的播放地址时，将传入应用自定义的播放组件进行播放。

#### 6.2 接口定义

```
+(void)addVideoPlayRequestListener:(NASVideoPlayRequestBlock)listener
```

#### 6.3 调用示例
```
[NASSDKNative addVideoPlayRequestListener:^(NSString *videoName, NSString *videoURL, NASVideoPlayResponseBlock responseBlock) {
     NASVideoPlayViewController* videoPlayVC = [[NASVideoPlayViewController alloc] initWithVideoUrl:videoURL];
     UINavigationController* navi = [[UINavigationController alloc] initWithRootViewController:videoPlayVC];
     [self.window.rootViewController presentViewController:navi animated:YES completion:nil];
      //播放成功回调
      responseBlock(YES,nil);
}];
```
* 注：SDK采用内部转流的方式对视频数据进行规范化处理，所以应用接收到的将是以`http://localhost`开头的视频播放地址。

--------------------

### 7 一键唤起文件选择器
#### 7.1 接口描述
通过传递文件uri列表，拉起目录选择器进行文件一键转存到云nas

#### 7.2 接口定义

```
+(void)openFileUpload:(NSArray *)paths WithCompletion:(NASCompletionBlock)completion
```

#### 7.4 调用示例

```
[NASSDKNative openFileUpload:paths WithCompletion:^(NSInteger resultCode, NSString *resultMsg) {
    if (resultCode == NAS_RESULT_SUCCESS) {
        NSLog(@"调用文件选择器成功");
    }
    else{
        NSLog(@"调用文件选择器失败");
    }
}];
```
* 注：paths 是沙盒内可访问文件的绝对路径数组

--------------------

## 错误码对照表

| 错误码 | 描述 |
| :------: | :------ |
| 200 | 调用成功 |
| 400 | 请求错误 |
| 3000 | flutter插件未注册 |
| 3001 | flutter引擎异常 |
| 4000 | 请求参数错误 |
| 4001 | 用户未开通云nas服务 |
| ~~4002~~ | ~~用户重复退出~~ |
| 4003 | 用户退出失败 |
| 4004 | 方法未实现 |
| 4005 | 数据解析异常 |
| 9000 | 其他错误 |


