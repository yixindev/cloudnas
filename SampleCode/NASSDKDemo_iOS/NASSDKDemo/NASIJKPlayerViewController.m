//
//  NASIJKPlayerViewController.m
//  NASSDK_Example
//
//  Created by 金华 on 2021/6/7.
//  Copyright © 2021 yixinDeveloper. All rights reserved.
//

#import "NASIJKPlayerViewController.h"
#import <ZFPlayer/ZFPlayer.h>
#import <ZFIJKPlayerManager.h>
#import <ZFPlayerControlView.h>

@interface NASIJKPlayerViewController ()

@property (copy,nonatomic)NSString* url;

@property (strong,nonatomic)ZFPlayerController* playerController;

@property (strong,nonatomic)ZFIJKPlayerManager* playerManager;

@end

@implementation NASIJKPlayerViewController


-(void)dealloc{
    _playerController = nil;
    _playerManager = nil;
}


-(instancetype)initWithVideoUrl:(NSString*)url{
    self = [super init];
    if (self) {
        _url = url;
    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"IJKPlayer";
    
    [self setupPlayerController];
}


-(void)viewDidDisappear:(BOOL)animated{
    [_playerController stop];
    [_playerManager stop];
}


- (void)setupPlayerController{
    ZFIJKPlayerManager* playerManager = [[ZFIJKPlayerManager alloc] init];
    _playerManager = playerManager;
    playerManager.shouldAutoPlay = NO;
    playerManager.assetURL = [NSURL URLWithString:_url];
    
    //处理h.265编码音画不同步的问题
    [playerManager.options setPlayerOptionIntValue:5 forKey:@"framedrop"];
    
    ZFPlayerController *playerController = [ZFPlayerController playerWithPlayerManager:playerManager containerView:self.view];
    _playerController = playerController;
    
    ZFPlayerControlView* controlView = [ZFPlayerControlView new];
    controlView.fastViewAnimated = YES;
    controlView.prepareShowLoading = YES;
    controlView.prepareShowControlView = NO;
    _playerController.controlView = controlView;
    
    [playerManager prepareToPlay];
    [playerManager play];
}


@end
