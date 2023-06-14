//
//  SMBClient.h
//  SMBDemo
//
//  Created by 黄宣冬 on 2021/6/22.
//

#import <Foundation/Foundation.h>
#import "YXSMBFileInfo.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^YXSMBCompletionHandler)( NSError* _Nullable error);

@interface YXSMBClient : NSObject

@property (nonatomic, readonly) BOOL isConnecting;

+ (instancetype)shared;
- (void)connectWithUrl:(NSString *)url userName:(NSString *)userName password:(NSString *)password completion:(void (^)(NSError * _Nonnull error))completion;
- (void)disconnectWithCompletionHandler:(void (^)(NSError * _Nullable error))completion;
- (void)listSubpathsOfPath:(NSString *)path completionHandler:(void (^)(NSArray<NSDictionary<NSURLResourceKey,id> *> * _Nullable contents, NSError * _Nullable error))completion;
- (void)downloadFileAppend:(NSString *)url toPath:(NSString *)path offset:(int64_t)offset buffsize:(int64_t)buffsize progress:(BOOL (^)(int64_t bytes, int64_t total))progress completion:(void (^)(NSError * _Nonnull error))completion;
- (void)downloadFile:(NSString *)url toPath:(NSString *)path progress:(BOOL (^)(int64_t bytes, int64_t total))progress completion:(void (^)(NSError * _Nonnull error))completion;

- (void)downloadFile:(NSString*)url
               range:(NSRange)range
     receivedHandler:(BOOL(^)(int64_t offset,int64_t total,NSData* data))receivedHandler
   completionHandler:(YXSMBCompletionHandler)completionHandler;

- (void)fetchFileInfo:(NSString*)filePath
           completion:(void(^)(YXSMBFileInfo* _Nullable fileInfo ,NSError* _Nullable error))completion;

- (void)changeFileName:(NSString *)source targetName:(NSString *)targetName completion:(void (^)(NSError * _Nonnull error))completion;

- (void)uploadFile:(NSString *)filePath toURL:(NSString *)toURL append:(BOOL)append progress:(BOOL (^)(int64_t bytes, int64_t total))progress completion:(void (^)(NSError * _Nonnull error))completion;

- (void)uploadFileAppend:(NSData *)content url:(NSString *)url append:(BOOL)append progress:(BOOL (^)(int64_t bytes, int64_t total))progress completion:(void (^)(NSError * _Nonnull error))completion;

- (void)uploadFilePath:(NSString *)path url:(NSString *)url append:(BOOL)append offset:(NSInteger)offset size:(NSInteger)size progress:(BOOL (^)(int64_t bytes, int64_t total))progress completion:(void (^)(NSError * _Nonnull error))completion;

@end

NS_ASSUME_NONNULL_END
