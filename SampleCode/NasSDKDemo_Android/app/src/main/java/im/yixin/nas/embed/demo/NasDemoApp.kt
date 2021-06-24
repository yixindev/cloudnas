package im.yixin.nas.embed.demo

import android.app.Application
import android.content.Context
import android.util.Log
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.sdk.YXNasSDK
import im.yixin.nas.sdk.api.*

/**
 * Created by jixia.cai on 2021/2/22 5:16 PM
 */
class NasDemoApp : Application() {

    companion object {

        var TAG = "Nas::Demo"

        lateinit var sContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        sContext = this
        Log.i(TAG, "start init nas-sdk when appstart ~")
        NasInvocationProxy.instance.init(this@NasDemoApp)
        YXNasSDK.instance.init(this, object : INasInvokeCallback<Void> {

            override fun onResult(code: Int, message: String?, data: Void?) {
                Log.i(TAG, "init nas-sdk with code: $code, message: $message ~")
                NasInvocationProxy.instance.notifySDKInitResult(code, message)
            }
        })

//        YXNasSDK.instance.serverEnv = ServerEnv.online //设置调试环境

        //添加视频播放回调
        YXNasSDK.instance.setVideoPlayListener(object : IVideoPlayListener {
            override fun onVideoPlayCallback(
                videoURL: String,
                videoName: String?,
                methodCall: IVideoPlayCall?
            ) {
                Log.i(
                    TAG,
                    "response flutter video play with URL: $videoURL, name: $videoName ~"
                )
                //播放视频通过methodCall 回调播放结果
                if (videoURL.isNotEmpty()) {
                    methodCall?.success()
                } else {
                    methodCall?.error(
                        "视频播放失败，URL解析异常"
                    )
                }
            }

        })
    }
}