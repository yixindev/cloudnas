//
//  NASSDKNative.h
//  NASSDK
//
//  Created by 金华 on 2022/7/29.
//

#import <Foundation/Foundation.h>
#import <cloud_nas/NASDefines.h>

NS_ASSUME_NONNULL_BEGIN

@interface NASSDKNative : NSObject

/// 获取SDK界面容器实例
+(UIViewController*)contentViewController;


/// 初始化SDK
/// @param appKey 应用程序唯一标识
/// @param completion 初始化结果回调
+(void)initWithAppKey:(NSString*)appKey
           completion:(NASCompletionBlock)completion;



/// 设置授权令牌
/// @param token 授权令牌
/// @param completion 登录结果回调
+(void)setAuthToken:(NSString*)token
               type:(NASAuthType)type
         completion:(NASCompletionBlock)completion;


/// 退出登录
/// @param completion 退出登录结果回调
+(void)logoutWithCompletion:(NASCompletionBlock)completion;


/// 设置SDK请求视频播放的回调，当SDK中点击视频需要播放时，会进行调用
/// @param listener 请求回调
+(void)addVideoPlayRequestListener:(NASVideoPlayRequestBlock)listener;


/// 设置SDK请求天翼网盘tokenCode的回调，当SDK需要天翼网盘授权时，会进行调用。
/// @param listener 请求回调
+(void)addTokenCodeRequestListener:(NASTokenCodeRequestBlock)listener;


/// 相册文件上传
/// @param urls 文件 url 数组
/// @param completion 结果回调
+(void)openFileUpload:(NSArray *)urls WithCompletion:(NASCompletionBlock)completion;

@end

NS_ASSUME_NONNULL_END
