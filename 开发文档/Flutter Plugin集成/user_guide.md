## 概述
云NAS提供了一套简单易用的接口，允许flutter开发者通过插件集成的方式进行调用，快速地集成云NAS的界面和功能。

## 快速接入
### 版本要求
| 名称 | 要求 |
| :--- | :--- |
| dart sdk | >=2.14.0 <3.0.0 |
| flutter | 2.5.2 |
| iOS | >= 10.0 |

### 接入指南
#### flutter工程

1.在flutter主项目本地目录中合适的位置新建文件夹``cloud_nas``, 在``cloud_nas``文件夹中执行命令：

```
git submodule add git@github.com:yixindev/cloud_nas_plugin.git
```

2.在flutter主项目的配置文件**pubspec.yaml**中添加本地插件依赖：

```
cloud_nas:
path: ./pub/cloud_nas_plugin(这里替换为你的本地路径)
```
3.执行flutter命令

```
flutter pub get
```
4.在**main.dart**文件中新增入口方法：

```
@pragma("vm:entry-point")
void mainNas() {
  CloudNas.runApp();
}
```

#### iOS工程

1.在flutter主工程编译成功的前提下，在iOS主工程相关位置引入头文件：

```
#import <cloud_nas/NASSDKPlugin.h>
```
2.创建flutter引擎，并指定入口函数，推荐使用**FlutterEngineGroup**生成轻量级引擎:

```
self.engineGroup = [[FlutterEngineGroup alloc] initWithName:@"ios.flutter" project:nil];
self.nasEngine = [self.engineGroup makeEngineWithEntrypoint:@"mainNas" libraryURI:nil];
[self.nasEngine run];
[GeneratedPluginRegistrant registerWithRegistry:self.nasEngine];

```
3.调用原生接口的初始化方法，传入flutter引擎等参数：

```
[NASSDKPlugin initWithAppKey:APP_KEY
               flutterEngine:self.nasEngine
                  completion:^(NSInteger resultCode, NSString *resultMsg) {
    ...
}]
```

### android工程