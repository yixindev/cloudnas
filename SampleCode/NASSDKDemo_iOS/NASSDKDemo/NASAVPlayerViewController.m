//
//  NASAVPlayerViewController.m
//  NASSDK_Example
//
//  Created by 金华 on 2021/6/7.
//  Copyright © 2021 yixinDeveloper. All rights reserved.
//

#import "NASAVPlayerViewController.h"
#import <AVKit/AVKit.h>

@interface NASAVPlayerViewController ()

@property (copy,nonatomic)NSString* url;

@property (strong,nonatomic)AVPlayerViewController* playerController;

@end

@implementation NASAVPlayerViewController

-(instancetype)initWithVideoUrl:(NSString*)url{
    self = [super init];
    if (self) {
        _url = url;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"AVPlayer";

    [self setupPlayerController];
}


-(void)setupPlayerController{
    AVPlayerViewController* playerController = [[AVPlayerViewController alloc] init];
    _playerController = playerController;
    playerController.view.frame = self.view.bounds;
    [self.view addSubview:playerController.view];
    playerController.showsPlaybackControls = YES;
    
    AVPlayer* player = [AVPlayer playerWithURL:[NSURL URLWithString:_url]];
    _playerController.player = player;
    [_playerController.player play];
}


@end
