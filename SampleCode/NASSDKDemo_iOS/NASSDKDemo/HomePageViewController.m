//
//  HomePageViewController.m
//  NASSDK_Example
//
//  Created by 金华 on 2021/11/26.
//  Copyright © 2021 yixinDeveloper. All rights reserved.
//

#import "HomePageViewController.h"
#import <NASSDK/NASSDKNative.h>
#import "NASVideoPlayViewController.h"

#define APP_KEY @"appkey"

@interface HomePageViewController ()

@property (copy,nonatomic)NSString* token;

@end

@implementation HomePageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}


- (IBAction)onOpenCloudNas:(id)sender {
    
    __weak typeof(self) weakSelf = self;
    [self fetchTokenWithCompletion:^(NSString *token) {
        
        if (token == nil || token.length == 0) {
            return;
        }
        
        weakSelf.token = token;
        
//        UIViewController* sdkVC = [NASSDKNative contentViewController];
//        sdkVC.modalPresentationStyle = UIModalPresentationFullScreen;
//        [weakSelf presentViewController:sdkVC animated:YES completion:nil];
        
        [weakSelf NASSDKInit];
    }];
}


#pragma mark - sdk handle

//SDK初始化
-(void)NASSDKInit{
    [NASSDKNative initWithAppKey:APP_KEY completion:^(NSInteger resultCode, NSString *resultMsg) {
        //SDK初始化成功
        if (resultCode == NAS_RESULT_SUCCESS) {
            
            [self NASSDKAuth];
            [self NASSDKVideoPlayRequest];
            [self NASSDKTokenCodeRequest];
        }
        //SDK初始化失败
        else{
            
        }
    }];
}


//SDK授权
-(void)NASSDKAuth{
    
    [NASSDKNative setAuthToken:self.token type:NASAuthTypeCloudBroadband completion:^(NSInteger resultCode, NSString *resultMsg) {
        //授权成功
        if(resultCode == NAS_RESULT_SUCCESS){
            //推出 [NASSDKNative contentViewController]
            UIViewController* sdkVC = [NASSDKNative contentViewController];
            sdkVC.modalPresentationStyle = UIModalPresentationFullScreen;
            [self presentViewController:sdkVC animated:YES completion:nil];
        }
        //用户未开通
        else if(resultCode == NAS_RESULT_USER_NOT_EXIST){
            //跳转开通入口
        }
        //其他错误
        else{
            
        }
        
        NSLog(@"logon result code %ld, message %@",resultCode,resultMsg);
    }];
}



//SDK退出登录
-(void)NASSDKLogout{
    [NASSDKNative logoutWithCompletion:^(NSInteger resultCode, NSString *resultMsg) {
        //SDK退出登录成功
        if (resultCode == NAS_RESULT_SUCCESS) {

        }
        //SDK退出登录失败
        else{
            
        }
    }];
}

//监听SDK 视频播放请求
-(void)NASSDKVideoPlayRequest{
    [NASSDKNative addVideoPlayRequestListener:^(NSString *videoName, NSString *videoURL, NASVideoPlayResponseBlock responseBlock) {
        NSLog(@"request play video : %@ ,url :%@",videoName,videoURL);
        
        NASVideoPlayViewController* videoPlayVC = [[NASVideoPlayViewController alloc] initWithVideoUrl:videoURL];
        UINavigationController* navi = [[UINavigationController alloc] initWithRootViewController:videoPlayVC];
        navi.modalPresentationStyle = UIModalPresentationFullScreen;
        
        if (self.presentedViewController != nil) {
            [self.presentedViewController presentViewController:navi animated:YES completion:nil];
        }
                
        responseBlock(YES,nil);
    }];
}

//监听SDK tokenCode请求
-(void)NASSDKTokenCodeRequest{
    [NASSDKNative addTokenCodeRequestListener:^(NASTokenCodeResponseBlock responseBlock) {
        [self fetchTokenCodeWithCompletion:^(NSString *tokenCode) {
            responseBlock(tokenCode);
        }];
    }];
}



#pragma mark - private

-(void)fetchTokenWithCompletion:(void(^)(NSString* token))completion{

    UIAlertController* alertController = [UIAlertController alertControllerWithTitle:nil message:@"请输入小翼管家token" preferredStyle:UIAlertControllerStyleAlert];
    [alertController addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"请输入小翼管家token";
        textField.text = @"";
    }];
    [alertController addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        UITextField* tokenTextField = alertController.textFields.firstObject;
        if (completion) {
            completion(tokenTextField.text);
        }
    }]];
    [alertController addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil]];
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:alertController animated:YES completion:nil];
}


-(void)fetchTokenCodeWithCompletion:(void(^)(NSString* tokenCode))completion{

    UIAlertController* alertController = [UIAlertController alertControllerWithTitle:nil message:@"请输入小翼管家tokenCode" preferredStyle:UIAlertControllerStyleAlert];
    [alertController addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"请输入小翼管家tokenCode";
    }];
    [alertController addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        UITextField* tokenTextField = alertController.textFields.firstObject;
        if (completion) {
            completion(tokenTextField.text)             ;
        }
    }]];
    [alertController addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil]];
    if (self.presentedViewController != nil) {
        [self.presentedViewController presentViewController:alertController animated:YES completion:nil];
    }
}


@end
