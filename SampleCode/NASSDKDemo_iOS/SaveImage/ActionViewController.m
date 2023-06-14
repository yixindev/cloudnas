//
//  ActionViewController.m
//  SaveImage
//
//  Created by 黄宣冬 on 2023/6/14.
//

#import "ActionViewController.h"
#import <MobileCoreServices/MobileCoreServices.h>

@interface ActionViewController ()

@property(strong,nonatomic) IBOutlet UIImageView *imageView;
@property(strong,nonatomic) NSMutableArray *files;

@end

@implementation ActionViewController

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [self saveImageToFileManager];
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
}

#pragma mark - privates -

- (void)saveImageToFileManager {
    
    _files = @[].mutableCopy;
    
    __weak typeof(self) weakself = self;
    
    [self clearImages];
    
    for (NSExtensionItem *item in self.extensionContext.inputItems) {
        for (NSItemProvider *itemProvider in item.attachments) {
            if ([itemProvider hasItemConformingToTypeIdentifier:@"public.image"]) {
                [itemProvider loadItemForTypeIdentifier:@"public.image" options:nil completionHandler:^(NSURL *url, NSError *error) {
                    if (url )
                    [weakself saveFile:url count:item.attachments.count];
                }];
            } else if ([itemProvider hasItemConformingToTypeIdentifier:@"public.movie"]) {
                [itemProvider loadItemForTypeIdentifier:@"public.movie" options:nil completionHandler:^(NSURL *url, NSError *error) {
                    [weakself saveFile:url count:item.attachments.count];
                }];
            }
        }
    }
}

- (void)clearImages {
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSURL *containerURL = [fileManager containerURLForSecurityApplicationGroupIdentifier:[self groupId]];
    if (!containerURL) {
        return;
    }
    
    NSURL *imageURL = [containerURL URLByAppendingPathComponent:@"image"];
    
    if ([fileManager fileExistsAtPath:imageURL.path] == YES) {
        NSError *error = nil;
        [fileManager removeItemAtURL:imageURL error:&error];
    }
    
    NSError *error = nil;
    BOOL result = [fileManager createDirectoryAtURL:imageURL withIntermediateDirectories:YES attributes:nil error:&error];
    if (result == NO) {
        NSLog(@"error:%@", error);
    }
}

- (void)saveFile:(NSURL *)url count:(NSInteger)count {
    if(url) {
        [self.files addObject:url];
        [self saveFileToAppGroup:url];
        
        if (self.files.count == count) {
            [self openUrl:nil];
        }
    }
}

- (void)saveFileToAppGroup:(NSURL *)url {
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSURL *containerURL = [fileManager containerURLForSecurityApplicationGroupIdentifier:[self groupId]];
    if (!containerURL) {
        return;
    }
    
    NSString *path = [containerURL.path stringByAppendingFormat:@"/image/%@", url.lastPathComponent];
    
    NSError *error = nil;
    [fileManager copyItemAtPath:url.path toPath:path error:&error];
    
    if (error) {
        NSLog(@"移动错误%@",error);
    }
}

- (void)openUrl:(NSString *)url {
    UIResponder * responder = [self nextResponder];
    while ((responder = [responder nextResponder]) != nil) {
        if ([responder respondsToSelector:@selector(openURL:)] == YES) {
            NSString *openUrlStr = @"nasdemo://upload";
            NSURL *openUrl =[NSURL URLWithString:openUrlStr];
            [responder performSelector:@selector(openURL:)withObject:openUrl];
            [self.extensionContext completeRequestReturningItems:@[]completionHandler:nil];
            return;
        }
    }
}

- (NSString *)groupId {
    return @"group.com.yixin.cloudNas";
}

#pragma mark - actions -

- (IBAction)done {
    // Return any edited content to the host app.
    // This template doesn't do anything, so we just echo the passed in items.
    [self.extensionContext completeRequestReturningItems:self.extensionContext.inputItems completionHandler:nil];
}

@end
