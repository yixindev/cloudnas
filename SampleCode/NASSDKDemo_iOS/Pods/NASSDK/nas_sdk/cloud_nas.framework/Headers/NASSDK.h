//
//  NASSDK.h
//  NASSDK
//
//  Created by 金华 on 2021/3/10.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <Flutter/Flutter.h>
#import "NASDefines.h"

NS_ASSUME_NONNULL_BEGIN

@interface NASSDK : NSObject

/// 获取SDK实例
+(instancetype)sharedInstance;


/// 获取SDK界面容器实例
-(UIViewController*)contentViewController;


/// 初始化SDK
/// @param appKey 应用程序唯一标识
/// @param engine 用来启动flutter模块的engine
/// @param completion 初始化结果回调
-(void)initWithAppKey:(NSString*)appKey
        flutterEngine:(FlutterEngine*)engine
           completion:(NASCompletionBlock)completion;



/// 设置授权令牌
/// @param token 令牌
/// @param type 授权类型
/// @param completion 授权结果回调
-(void)setAuthToken:(NSString*)token
               type:(NASAuthType)type
         completion:(NASCompletionBlock)completion;


/// 通过账号密码授权
/// @param account 账号
/// @param password 密码
/// @param areaNo 区域码
/// @param completion 授权结果回调
-(void)authWithAccount:(NSString*)account
              password:(NSString*)password
                areaNo:(NSString*)areaNo
            completion:(NASCompletionBlock)completion;

/// 退出登录
/// @param urls 文件 url 数组
/// @param completion 结果回调
- (void)openFileUpload:(NSArray *)urls WithCompletion:(NASCompletionBlock)completion;


/// 退出登录
/// @param completion 退出登录结果回调
-(void)logoutWithCompletion:(NASCompletionBlock)completion;


/// 设置SDK请求视频播放的回调，当SDK中点击视频需要播放时，会进行调用
/// @param listener 请求回调
-(void)addVideoPlayRequestListener:(NASVideoPlayRequestBlock)listener;


/// 设置SDK请求天翼网盘tokenCode的回调，当SDK需要天翼网盘授权时，会进行调用。
/// @param listener 请求回调
-(void)addTokenCodeRequestListener:(NASTokenCodeRequestBlock)listener;


@end

NS_ASSUME_NONNULL_END
