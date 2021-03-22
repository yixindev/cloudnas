package im.yixin.nas.embed.demo

import android.app.Application
import android.content.Context
import android.util.Log
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.sdk.YXNasSDK
import im.yixin.nas.sdk.api.IMethodCall
import im.yixin.nas.sdk.api.INasInvokeCallback
import im.yixin.nas.sdk.api.ITokenRequestListener
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
        YXNasSDK.instance.setTokenRequestListener(object : ITokenRequestListener {
            override fun onTokenRequest(methodCall: IMethodCall<String>?) {
                Log.i(
                    TAG,
                    "response flutter refresh-token ~"
                )
                //获取当前用户信息
                val userInfo = NasInvocationProxy.instance.getCurrentUserInfo()
                if (userInfo == null) {
                    methodCall?.error(
                        YXNasConstants.ResultCode.CODE_REFRESH_TOKEN_ERROR,
                        "当前用户数据为空"
                    )
                } else {
                    val isLogin = userInfo.isLogin
                    if (isLogin == null || isLogin == false) {
                        methodCall?.error(
                            YXNasConstants.ResultCode.CODE_REFRESH_TOKEN_ERROR,
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
                                            YXNasConstants.ResultCode.CODE_MOCK_TOKEN_ERROR,
                                            "刷新token失败: $message"
                                        )
                                    }
                                }
                            })
                    }
                }
            }
        })
    }
}