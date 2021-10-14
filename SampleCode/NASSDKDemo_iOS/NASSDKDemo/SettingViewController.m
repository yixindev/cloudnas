//
//  SettingViewController.m
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import "SettingViewController.h"

@interface SettingViewController ()

@end

@implementation SettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"设置";
}

- (IBAction)logoutButtonPressed:(id)sender {
    if (_delegate && [_delegate respondsToSelector:@selector(logoutSuccess)]) {
        [_delegate logoutSuccess];
    }
}

@end
