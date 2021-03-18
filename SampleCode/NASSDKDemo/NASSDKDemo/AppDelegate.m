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

@interface AppDelegate ()<LoginViewControllerDelegate,SettingViewControllerDelegate>

@property (copy,nonatomic)NSString* mobile;

@property (copy,nonatomic)NSString* token;

@property (assign,nonatomic)BOOL sdkInit;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    LoginViewController* loginVC = [[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil];
    loginVC.delegate = self;
    self.window.rootViewController = loginVC;
    [self.window makeKeyAndVisible];
    
    [self initNASSDK];
    
    return YES;
}


#pragma mark - LoginViewControllerDelegate

-(void)loginSuccess:(NSString *)mobile{
    self.mobile = mobile;
        
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
    
    [self fetchToken];
}


#pragma mark - SettingViewControllerDelegate

-(void)logoutSuccess{
    self.mobile = nil;
    self.token = nil;
    
    LoginViewController* loginVC = [[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil];
    loginVC.delegate = self;
    self.window.rootViewController = loginVC;
    
    [self logoutNASSDK];
}



#pragma mark - sdk handle

//SDK初始化
-(void)initNASSDK{
    NSString* appKey = @"appkey";
    [[NASSDK sharedInstance] initializeWithAppKey:appKey completion:^(NSInteger resultCode, NSString *resultMsg) {
        if (resultCode == NAS_RESULT_SUCCESS) {
            self.sdkInit = YES;
            [self fetchToken];
        }
    }];
}


//SDK登录授权
-(void)authNASSDK{
    if (self.mobile.length && self.token.length) {
        [[NASSDK sharedInstance] authWithMobile:self.mobile token:self.token completion:^(NSInteger resultCode, NSString *resultMsg) {
            if (resultCode == NAS_RESULT_SUCCESS) {
                //SDK登录成功
                [self handleNASSDKTokenExpired];
            }
            else{
                //SDK登录失败
            }
        }];
    }
}

//SDK登出
-(void)logoutNASSDK{
    [[NASSDK sharedInstance] logoutWithCompletion:^(NSInteger resultCode, NSString *resultMsg) {
        if (resultCode == NAS_RESULT_SUCCESS) {
            //SDK退出登录成功
        }
        else{
            //SDK退出登录失败
        }
    }];
}


//监听SDK token过期
-(void)handleNASSDKTokenExpired{
    [[NASSDK sharedInstance] addTokenExpiredListener:^(NASTokenSendBlock sendBlock) {
        //模拟从服务器获取token
        [[NASSDK sharedInstance] mockTokenFetchWithMobile:self.mobile completion:^(NSString *token) {
            sendBlock(token);
        }];
    }];
}




-(void)fetchToken{
    if (_sdkInit && _token.length == 0 && _mobile.length > 0) {
        //模拟从服务器获取token
        [[NASSDK sharedInstance] mockTokenFetchWithMobile:_mobile completion:^(NSString *token) {
            self.token = token;
            [self authNASSDK];
        }];
    }
}


@end
