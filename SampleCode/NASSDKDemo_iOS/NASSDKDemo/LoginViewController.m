//
//  LoginViewController.m
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import "LoginViewController.h"
#import "HomePageViewController.h"

@interface LoginViewController ()

@property (weak, nonatomic) IBOutlet UITextField *mobileTextField;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}


- (IBAction)loginButtonPressed:(id)sender {
    
    HomePageViewController* homePageVC = [[HomePageViewController alloc] initWithNibName:@"HomePageViewController" bundle:nil];
    UINavigationController* navi = [[UINavigationController alloc] initWithRootViewController:homePageVC];
    [UIApplication sharedApplication].keyWindow.rootViewController = navi;
}

@end
