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

#import "NasStreamFlutterPlugin.h"

FOUNDATION_EXPORT double nas_stream_flutterVersionNumber;
FOUNDATION_EXPORT const unsigned char nas_stream_flutterVersionString[];

