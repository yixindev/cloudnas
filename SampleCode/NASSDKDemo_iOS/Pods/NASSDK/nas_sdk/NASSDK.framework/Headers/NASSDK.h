//
//  NASSDK.h
//  NASSDK
//
//  Created by 金华 on 2021/3/10.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "NASDefines.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSInteger,NASAuthType){
    NASAuthTypeUniversal = 0,          //通用
    NASAuthTypeXiaoYi = 1,             //小翼管家
};

@interface NASSDK : NSObject


/// 获取SDK实例
+(instancetype)sharedInstance;


/// 获取SDK界面容器实例
-(UIViewController*)contentViewController;


/// 初始化SDK
/// @param appKey 应用程序唯一标识
/// @param completion 初始化结果回调
-(void)initWithAppKey:(NSString*)appKey
           completion:(NASCompletionBlock)completion;



/// 设置授权令牌
/// @param token 令牌
/// @param type 授权类型
/// @param completion 登录结果回调
-(void)setAuthToken:(NSString*)token
               type:(NASAuthType)type
         completion:(NASCompletionBlock)completion;


/// 退出登录
/// @param completion 退出登录结果回调
-(void)logoutWithCompletion:(NASCompletionBlock)completion;



/// 设置SDK请求视频播放的回调，当SDK中点击视频需要播放时，会进行调用
/// @param listener 请求回调
-(void)addVideoPlayRequestListener:(NASVideoPlayRequestBlock)listener;


/// 设置SDK请求tokenCode的回调，当SDK需要使用tokenCode时，会进行调用。
/// @param listener 请求回调
-(void)addTokenCodeRequestListener:(NASTokenCodeRequestBlock)listener;


@end

NS_ASSUME_NONNULL_END
