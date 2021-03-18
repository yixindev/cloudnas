//
//  SettingViewController.h
//  NASSDKDemo
//
//  Created by 金华 on 2021/3/17.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@protocol SettingViewControllerDelegate <NSObject>

-(void)logoutSuccess;

@end

@interface SettingViewController : UIViewController

@property (weak,nonatomic)id<SettingViewControllerDelegate> delegate;

@end

NS_ASSUME_NONNULL_END
