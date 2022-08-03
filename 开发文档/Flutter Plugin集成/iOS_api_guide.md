## 接口指南


### 引入头文件
```
#import <cloud_nas/NASSDK.h>
```

### 1 获取全局实例

#### 1.1 接口描述

获取SDK的全局实例对象。

#### 接口定义

```
+(instancetype)sharedInstance;
```

### 2 获取界面容器

#### 2.1 接口描述

获取SDK的业务界面，返回值是UIViewController类的实例，将获取的对象添加到相应的位置，例如：TabbarController。

#### 2.2 接口定义

```
-(UIViewController*)contentViewController;
```

#### 2.3 注意事项

- 容器内部的导航栏样式和跳转由SDK内部定制，不跟随App的全局样式。

--------------------

### 3 初始化

#### 3.1 描述

在调用SDK其他接口之前，首先需要传入应用的**appKey**以及**flutterEngine**完成初始化。

#### 3.2 接口定义

```
-(void)initWithAppKey:(NSString*)appKey
        flutterEngine:(FlutterEngine*)engine
           completion:(NASCompletionBlock)completion;
```

#### 3.3 调用示例

```
[[NASSDK sharedInstance] initializeWithAppKey:appKey
                     			  flutterEngine:nasEngine
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

- **flutterEngine**在传入之前，必须先调用Flutter原生插件的初始化方法：

```
[GeneratedPluginRegistrant registerWithRegistry:self.nasEngine];
```

--------------------

### 4 设置授权令牌

#### 4.1 接口描述

设置SDK登录授权所必须的token、授权类型以及登录结果回调。

目前小翼管家接入授权类型为**NASAuthTypeXiaoYi**，其他接入方授权类型为**NASAuthTypeUniversal**。

#### 4.2 接口定义

```
-(void)setAuthToken:(NSString*)token
               type:(NASAuthType)type
         completion:(NASCompletionBlock)completion;
```

#### 4.3 调用示例

```
[[NASSDK sharedInstance] setAuthToken:self.token 
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

#### 注意事项
- 根据接入方授权流程的不同，授权类型请确保输入正确。

--------------------

### 5 注销

#### 5.1 接口描述

请求SDK注销自己当前的登录账号，变成未登录状态。

#### 5.2 接口定义

```
-(void)logoutWithCompletion:(NASCompletionBlock)completion;
```

--------------------

### 6 监听视频播放请求
#### 6.1 接口描述
当SDK点击视频文件进行在线播放时，会调用传入的回调函数，并将处理后的视频地址作为参数传入，应用获取到视频的播放地址时，将传入应用自定义的播放组件进行播放。

#### 6.2 接口定义
```
-(void)addVideoPlayRequestListener:(NASVideoPlayRequestBlock)listener
```

#### 6.3 调用示例
```
[[NASSDK sharedInstance] addVideoPlayRequestListener:^(NSString *videoName, NSString *videoURL, NASVideoPlayResponseBlock responseBlock) {

     NASVideoPlayViewController* videoPlayVC = [[NASVideoPlayViewController alloc] initWithVideoUrl:videoURL];
     UINavigationController* navi = [[UINavigationController alloc] initWithRootViewController:videoPlayVC];
     [self.window.rootViewController presentViewController:navi animated:YES completion:nil];
      //播放成功回调
      responseBlock(YES,nil);
}];
```
* 注：SDK采用内部转流的方式对视频数据进行规范化处理，所以应用接收到的将是以`http://localhost`开头的视频播放地址。

--------------------

## 错误码对照表

| 错误码 | 描述 |
| :------: | :------ |
| 200 | 调用成功 |
| 400 | 请求错误 |
| 3000 | flutter插件未注册 |
| 3001 | flutter引擎异常 |
| 4001 | 用户登录失败 |
| 4002 | 用户重复退出 |
| 4003 | 用户退出失败 |
| 4004 | 方法未实现 |
| 4005 | 数据解析异常 |




