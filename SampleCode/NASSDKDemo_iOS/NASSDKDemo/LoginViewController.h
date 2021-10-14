//
//  LoginViewController.h
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@protocol LoginViewControllerDelegate <NSObject>

-(void)loginSuccess:(NSString*)token;

@end

@interface LoginViewController : UIViewController

@property (weak,nonatomic)id<LoginViewControllerDelegate> delegate;

@end

NS_ASSUME_NONNULL_END
