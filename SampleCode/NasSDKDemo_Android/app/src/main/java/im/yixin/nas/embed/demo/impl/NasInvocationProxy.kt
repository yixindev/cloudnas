package im.yixin.nas.embed.demo.impl

import android.content.Context
import im.yixin.nas.sdk.NasAuthType
import im.yixin.nas.sdk.YXNasSDK
import im.yixin.nas.sdk.api.INasInvokeCallback
import im.yixin.nas.sdk.const.YXNasConstants

/**
 * Created by jixia.cai on 2021/3/1 8:12 PM
 */
class NasInvocationProxy {

    companion object {
        val instance = NasInvocationProxy()
    }

    private var initialized = false

    private var context: Context? = null

    private var sdkInitSuccess = false

    private lateinit var userRepo: UserRepo

    private val sdkInitListeners = mutableListOf<OnNasSDKnitListener>()

    fun init(context: Context) {
        if (initialized.not()) {
            this.context = context
            userRepo = UserRepo(context)
            initialized = true
        }
    }

    private fun assertInit() {
        assert(initialized) {
            "proxy instance should be init first ~"
        }
    }

    fun getCurrentUserInfo(): DemoUserInfo? {
        assertInit()
        return userRepo.getCurrentInfo()
    }

    fun updateUserInfo(userInfo: DemoUserInfo?) {
        assertInit()
        userRepo.saveUserInfo(userInfo)
    }

    fun addNasSDKnitListener(listener: OnNasSDKnitListener?) {
        if (listener != null) {
            if (!sdkInitListeners.contains(listener)) {
                sdkInitListeners.add(listener)
            }
            if (sdkInitSuccess) {
                listener.onSDKInitSuccess()
            }
        }
    }

    fun removeNasSDKnitListener(listener: OnNasSDKnitListener?) {
        if (listener != null) {
            sdkInitListeners.remove(listener)
        }
    }

    fun notifySDKInitResult(code: Int, message: String?) {
        if (code == YXNasConstants.ResultCode.CODE_SUCCESS) {
            sdkInitSuccess = true
            sdkInitListeners.forEach {
                it.onSDKInitSuccess()
            }
        }
    }

    private var userListener: OnNasUserListener? = null

    fun setNasUserListener(listener: OnNasUserListener?) {
        userListener = listener
    }

    fun notifyUserAction(action: UserAction) {
        if (action == UserAction.authSuccess) {
            userListener?.onUserAuthSuccess()
        } else {
            userListener?.onUserLogoutSuccess()
        }
    }

    fun execUserLogin(token: String?, callback: INasInvokeCallback<Void>? = null) {
        YXNasSDK.instance.authLogin(
            token,
            authType = NasAuthType.TypeXiaoYi,
            object : INasInvokeCallback<Void> {
                override fun onResult(code: Int, message: String?, data: Void?) {
                    callback?.onResult(code, message, data)
                }
            })
    }

    fun execUserLogout(callback: INasInvokeCallback<Void>?) {
        YXNasSDK.instance.logout(object : INasInvokeCallback<Void> {
            override fun onResult(code: Int, message: String?, data: Void?) {
                callback?.onResult(code, message, data)
            }
        })
    }
}

interface OnNasSDKnitListener {

    fun onSDKInitSuccess()
}

interface OnNasUserListener {

    fun onUserAuthSuccess()

    fun onUserLogoutSuccess()
}

enum class UserAction {

    authSuccess, logoutSuccess
}