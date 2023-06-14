//
//  AppDelegate.m
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import "AppDelegate.h"
#import "LoginViewController.h"
#import "NASVideoPlayViewController.h"
#import <JLRoutes/JLRoutes.h>

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

- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options {
    if ([url.scheme isEqualToString:@"nasdemo"]) {
        
        [[JLRoutes globalRoutes] routeURL:url];
        
        return YES;
    }
    
    return NO;
}

@end
