//
//  AppDelegate.m
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import "AppDelegate.h"
#import "LoginViewController.h"
#import "SettingViewController.h"
#import <NASSDK/NASSDK.h>
#import "NASVideoPlayViewController.h"

@interface AppDelegate ()<LoginViewControllerDelegate,SettingViewControllerDelegate>

@property (copy,nonatomic)NSString* token;

@property (assign,nonatomic)BOOL sdkSetted;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    LoginViewController* loginVC = [[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil];
    loginVC.delegate = self;
    self.window.rootViewController = loginVC;
    [self.window makeKeyAndVisible];
    
    return YES;
}


#pragma mark - LoginViewControllerDelegate

-(void)loginSuccess:(NSString *)token{
    self.token = token;
        
    UITabBarController* tabBarVC = [[UITabBarController alloc] init];
    tabBarVC.view.backgroundColor = [UIColor whiteColor];
    
    UIViewController* sdkVC = [[NASSDK sharedInstance] contentViewController];
    sdkVC.edgesForExtendedLayout = UIRectEdgeNone;
    sdkVC.tabBarItem.title = @"云硬盘";
    sdkVC.tabBarItem.image = [UIImage imageNamed:@"ic_disk_normal"];
    sdkVC.tabBarItem.selectedImage = [UIImage imageNamed:@"ic_disk_sel"];
    sdkVC.tabBarItem.imageInsets = UIEdgeInsetsMake(17, 0, 17, 0);

    
    SettingViewController* settingVC = [[SettingViewController alloc] initWithNibName:@"SettingViewController" bundle:nil];
    settingVC.delegate = self;
    settingVC.tabBarItem.title = @"设置";
    settingVC.tabBarItem.image = [UIImage imageNamed:@"ic_mine_normal"];
    settingVC.tabBarItem.selectedImage = [UIImage imageNamed:@"ic_mine_sel"];
    settingVC.tabBarItem.imageInsets = UIEdgeInsetsMake(17, 0, 17, 0);
    UINavigationController* settingNavi = [[UINavigationController alloc] initWithRootViewController:settingVC];

    tabBarVC.viewControllers = @[sdkVC,settingNavi];
    
    self.window.rootViewController = tabBarVC;
    
    [self NASSDKInit];
}


#pragma mark - SettingViewControllerDelegate

-(void)logoutSuccess{
    self.token = nil;
    
    LoginViewController* loginVC = [[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil];
    loginVC.delegate = self;
    self.window.rootViewController = loginVC;
    
    [self NASSDKLogout];
}


#pragma mark - sdk handle

//SDK初始化
-(void)NASSDKInit{
    NSString* appKey = @"appkey";
    [[NASSDK sharedInstance] initWithAppKey:appKey completion:^(NSInteger resultCode, NSString *resultMsg) {
        //SDK初始化成功
        if (resultCode == NAS_RESULT_SUCCESS) {
            [self NASSDKAuth];
            [self NASSDKVideoPlayRequest];
        }
        //SDK初始化失败
        else{
            
        }
    }];
}


//SDK授权
-(void)NASSDKAuth{
    [[NASSDK sharedInstance] setAuthToken:self.token type:NASAuthTypeXiaoYi completion:^(NSInteger resultCode, NSString *resultMsg) {
        //SDK登录成功
        if (resultCode == NAS_RESULT_SUCCESS) {
        }
        //SDK登录失败
        else{
            
        }
    }];
}


//SDK退出登录
-(void)NASSDKLogout{
    self.sdkSetted = NO;
    [[NASSDK sharedInstance] logoutWithCompletion:^(NSInteger resultCode, NSString *resultMsg) {
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
    [[NASSDK sharedInstance] addVideoPlayRequestListener:^(NSString *videoName, NSString *videoURL, NASVideoPlayResponseBlock responseBlock) {
        
        NASVideoPlayViewController* videoPlayVC = [[NASVideoPlayViewController alloc] initWithVideoUrl:videoURL];
        UINavigationController* navi = [[UINavigationController alloc] initWithRootViewController:videoPlayVC];
        navi.modalPresentationStyle = UIModalPresentationFullScreen;
        [self.window.rootViewController presentViewController:navi animated:YES completion:nil];
        //播放成功回调
        responseBlock(YES,nil);
    }];
}

@end
