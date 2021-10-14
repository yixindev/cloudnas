
#flutter 基础混淆配置
-keep class io.flutter.app.** {*;}
-keep class io.flutter.plugin.** {*;}
-keep class io.flutter.util.** {*;}
-keep class io.flutter.view.** {*;}
-keep class io.flutter.** {*;}
-keep class io.flutter.plugins.** {*;}

## fijkplayer插件
-keep class tv.danmaku.ijk.media.player.** {*;}

## x5浏览器
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.** {*;}
-keep class com.tencent.tbs.** {*;}

-keep class im.yixin.nas.sdk.**{*;}
-keep class com.mr.flutter.plugin.filepicker.**{*;}

-keep class com.baseflow.permissionhandler.**{*;}