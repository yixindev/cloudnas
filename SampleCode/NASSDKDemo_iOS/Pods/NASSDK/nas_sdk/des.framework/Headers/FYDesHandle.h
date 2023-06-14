//
//  FYDesHandle.h
//  Runner
//
//  Created by elex on 2020/8/14.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface FYDesHandle : NSObject

+(instancetype)initWithKey:(NSString*)key;

/// 数据加密
/// @param text 待加密内容
-(NSString*)encryptWithText:(NSString *)text;



/// 数据解密
/// @param text 加密内容
-(NSString*)decryptWithText:(NSString *)text;

@end

NS_ASSUME_NONNULL_END
