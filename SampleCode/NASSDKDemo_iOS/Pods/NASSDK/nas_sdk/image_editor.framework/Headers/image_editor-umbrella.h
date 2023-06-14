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

#import "EditorUIImageHandler.h"
#import "FIConvertUtils.h"
#import "FIEPlugin.h"
#import "FIMerger.h"
#import "ImageEditorPlugin.h"

FOUNDATION_EXPORT double image_editorVersionNumber;
FOUNDATION_EXPORT const unsigned char image_editorVersionString[];

