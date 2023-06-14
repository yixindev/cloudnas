//
//  YXSMBFileInfo.h
//  YXSMBClient
//
//  Created by 金华 on 2021/7/7.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface YXSMBFileInfo : NSObject

@property (copy,nonatomic)NSString* fileName;

@property (assign,nonatomic)int64_t fileSize;

@property (strong,nonatomic)NSDate* contentModificationDate;

@property (strong,nonatomic)NSDate* creationDate;

@property (assign,nonatomic)BOOL isDirectory;

-(instancetype)initWithItemAttributes:(NSDictionary<NSURLResourceKey,id>*)attributes;

@end

NS_ASSUME_NONNULL_END
