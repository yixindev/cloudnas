//
//  LoginViewController.m
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import "LoginViewController.h"
#import <NASSDK/NASSDK.h>

@interface LoginViewController ()
@property (weak, nonatomic) IBOutlet UITextField *mobileTextField;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}


- (IBAction)loginButtonPressed:(id)sender {
    NSString* mobile = _mobileTextField.text;
    
    if (mobile.length > 0) {
        if (self.delegate && [self.delegate respondsToSelector:@selector(loginSuccess:)]) {
            [self.delegate loginSuccess:mobile];
        }
    }
}


//模拟从服务器获取Token
-(void)requestTokenFromServer:(NSString*)mobile{
    [[NASSDK sharedInstance] mockTokenFetchWithMobile:mobile completion:^(NSString *token) {
    }];
}


@end
