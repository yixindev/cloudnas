//
//  AppDelegate.m
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import "AppDelegate.h"
#import "LoginViewController.h"
#import "NASVideoPlayViewController.h"
#import <NASSDK/NASSDK.h>

@interface AppDelegate ()

@property (copy,nonatomic)NSString* token;

@property (assign,nonatomic)BOOL sdkSetted;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    LoginViewController* loginVC = [[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil];
    UINavigationController* navi = [[UINavigationController alloc] initWithRootViewController:loginVC];
    self.window.rootViewController = navi;
    [self.window makeKeyAndVisible];
    
    return YES;
}


@end
