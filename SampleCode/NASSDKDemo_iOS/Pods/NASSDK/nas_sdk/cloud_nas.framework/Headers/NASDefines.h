//
//  NASDefines.h
//  NASSDK
//
//  Created by 金华 on 2021/3/11.
//

#ifndef NASDefines_h
#define NASDefines_h

/// SDK 授权类型
typedef NS_ENUM(NSInteger,NASAuthType){
    NASAuthTypeUniversal = 0,          //通用
    NASAuthTypeXiaoYi = 1,             //小翼管家
    NASAuthTypeCloudBroadband = 2,     //电信云宽带
    NASAuthTypeAccount = 3,            //账号密码登录
};


/// SDK API的通用回调函数。
/// SDK提供的接口多为异步实现，在调用接口时，需要调用方实现一个该类型的回调函数在异步调用结束后进行调用。
///
/// @param resultCode 应用程序唯一标识
/// @param resultMsg 初始化结果回调
///
typedef void(^NASCompletionBlock)(NSInteger resultCode,NSString* resultMsg);


/// 发送tokenCode给SDK的回调函数
///
/// @param tokenCode 小翼管家tokenCode
///
typedef void(^NASTokenCodeResponseBlock)(NSString* tokenCode);


/// SDK 请求tokenCode的回调函数
///
/// @param responseBlock 发送tokenCode的回调函数
///
typedef void(^NASTokenCodeRequestBlock)(NASTokenCodeResponseBlock responseBlock);


/// SDK 视频播放结果的回调函数
///
/// @param success 播放视频是否成功
/// @param message 播放失败时的错误信息
///
typedef void(^NASVideoPlayResponseBlock)(BOOL success,NSString* message);


/// SDK 请求视频播放的回调函数
///
/// @param videoName 视频文件名称
/// @param videoURL 视频地址（本地文件地址或远程资源地址）
/// @param responseBlock 发送播放结果的回调函数
///
typedef void(^NASVideoPlayRequestBlock)(NSString* videoName,NSString* videoURL,NASVideoPlayResponseBlock responseBlock);



/// 模拟请求token的回调函数
typedef void(^NASMockTokenFetchBlock)(NSString* token);


///SDK通用错误码及其描述
typedef NS_ENUM(NSInteger,NASResultCode){
    
    ///调用成功
    NAS_RESULT_SUCCESS              = 200,
    
    ///flutter插件未注册
    NAS_FLUTTER_PLUGINS_UNREGISTER  = 3000,
    
    ///flutter引擎异常
    NAS_FLUTTER_ENGINE_EXCEPTION    = 3001,
    
    ///请求参数错误
    NAS_RESULT_BAD_REQUEST          = 4000,
    
    ///用户不存在（未开通云NAS）
    NAS_RESULT_USER_NOT_EXIST       = 4001,
    
    ///用户重复退出
    NAS_RESULT_USER_ALREADY_LOGOUT  = 4002,
    
    ///用户退出失败
    NAS_RESULT_USER_LOGOUT_ERROR    = 4003,
    
    ///方法未实现
    NAS_RESULT_METHOD_NOT_IMP       = 4004,
    
    ///数据解析异常
    NAS_RESULT_DATA_PARSE_ERROR     = 4005,
    
    ///其他错误
    NAS_RESULT_OTHER_ERRPR          = 9000,
    
};

#endif
