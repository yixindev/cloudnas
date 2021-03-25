package im.yixin.nas.embed.demo

import android.app.Application
import android.content.Context
import android.util.Log
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.sdk.ServerEnv
import im.yixin.nas.sdk.YXNasSDK
import im.yixin.nas.sdk.api.*
import im.yixin.nas.sdk.const.YXNasConstants
import im.yixin.nas.sdk.entity.UserToken

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

        //添加token刷新回调
        YXNasSDK.instance.setTokenRequestListener(object : ITokenRequestListener {
            override fun onTokenRequest(methodCall: ITokenRefreshCall?) {
                Log.i(
                    TAG,
                    "response flutter refresh-token ~"
                )
                //获取当前用户信息
                val userInfo = NasInvocationProxy.instance.getCurrentUserInfo()
                if (userInfo == null) {
                    methodCall?.error(
                        "当前用户数据为空"
                    )
                } else {
                    val isLogin = userInfo.isLogin
                    if (isLogin == null || isLogin == false) {
                        methodCall?.error(
                            "当前用户未登录"
                        )
                    } else {
                        val mobile = userInfo.mobile
                        YXNasSDK.instance.getTestApi()
                            .mockToken(mobile, object : INasInvokeCallback<UserToken> {
                                override fun onResult(
                                    code: Int,
                                    message: String?,
                                    data: UserToken?
                                ) {
                                    if (code == YXNasConstants.ResultCode.CODE_SUCCESS) {
                                        //刷新本地token
                                        val current =
                                            NasInvocationProxy.instance.getCurrentUserInfo()
                                        NasInvocationProxy.instance.updateUserInfo(
                                            current?.withToken(
                                                data?.accessToken
                                            )
                                        )
                                        methodCall?.success(data?.accessToken)
                                    } else {
                                        methodCall?.error(
                                            "刷新token失败: $message"
                                        )
                                    }
                                }
                            })
                    }
                }
            }
        })

        YXNasSDK.instance.serverEnv = ServerEnv.dev //设置测试环境

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