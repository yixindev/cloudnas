//
//  NASVideoPlayViewController.m
//  NASSDK_Example
//
//  Created by 金华 on 2021/6/4.
//  Copyright © 2021 yixinDeveloper. All rights reserved.
//

#import "NASVideoPlayViewController.h"
#import <AVKit/AVKit.h>
#import "NASAVPlayerViewController.h"
#import "NASIJKPlayerViewController.h"

@interface NASVideoPlayViewController ()

@property (copy,nonatomic)NSString* url;

@property (strong,nonatomic)AVPlayerViewController* playerController;

@end

@implementation NASVideoPlayViewController

-(instancetype)initWithVideoUrl:(NSString*)url{
    self = [super init];
    if (self) {
        _url = url;
    }
    return self; }


-(void)viewDidLoad{
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    self.title = @"播放器选择";
    
    UIButton* button = [[UIButton alloc] init];
    [button setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [button setTitle:@"关闭" forState:UIControlStateNormal];
    [button addTarget:self action:@selector(closeButtonPressed) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem* rightItem = [[UIBarButtonItem alloc] initWithCustomView:button];
    self.navigationItem.rightBarButtonItem = rightItem;
    

    CGFloat buttonWidth = 200;
    UIButton* avButton = [[UIButton alloc] init];
    [avButton setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
    avButton.frame= CGRectMake(self.view.bounds.size.width * 0.5 - buttonWidth * 0.5, 200, buttonWidth, 50);
    [avButton setTitle:@"AVPlayer播放器" forState:UIControlStateNormal];
    [avButton addTarget:self action:@selector(avButtonPressed) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:avButton];
    
    UIButton* ijkButton = [[UIButton alloc] init];
    [ijkButton setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
    ijkButton.frame= CGRectMake(self.view.bounds.size.width * 0.5 - buttonWidth * 0.5, 400, buttonWidth, 50);
    [ijkButton setTitle:@"IJKPlayer播放器" forState:UIControlStateNormal];
    [ijkButton addTarget:self action:@selector(ijkButtonPressed) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:ijkButton];

}


-(void)avButtonPressed{
    NASAVPlayerViewController* playerVC = [[NASAVPlayerViewController alloc] initWithVideoUrl:_url];
    [self.navigationController pushViewController:playerVC animated:YES];
}



-(void)ijkButtonPressed{
    NASIJKPlayerViewController* playerVC = [[NASIJKPlayerViewController alloc] initWithVideoUrl:_url];
    [self.navigationController pushViewController:playerVC animated:YES];
}



-(void)closeButtonPressed{
    [self.navigationController dismissViewControllerAnimated:YES completion:nil];
}


@end
