#ifdef __OBJC__
#import <UIKit/UIKit.h>
#else
#ifndef FOUNDATION_EXPORT
#if defined(__cplusplus)
#define FOUNDATION_EXPORT extern "C"
#else
#define FOUNDATION_EXPORT extern
#endif
#endif
#endif

#import "Md5FileChecksumPlugin.h"
#import "md5_file_checksum.h"
#import "pigeon.h"

FOUNDATION_EXPORT double md5_file_checksumVersionNumber;
FOUNDATION_EXPORT const unsigned char md5_file_checksumVersionString[];

