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

#import "CloudNasPlugin.h"
#import "NASDefines.h"
#import "NASSDK.h"

FOUNDATION_EXPORT double cloud_nasVersionNumber;
FOUNDATION_EXPORT const unsigned char cloud_nasVersionString[];

