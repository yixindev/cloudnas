package im.yixin.nas.embed.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kaopiz.kprogresshud.KProgressHUD
import im.yixin.nas.embed.demo.NasDemoApp
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.embed.demo.impl.UserAction
import im.yixin.nas.embed.demo.impl.UserRepo
import im.yixin.nas.embed.demo.util.ToastUtil
import im.yixin.nas.sdk.YXNasSDK
import im.yixin.nas.sdk.api.INasInvokeCallback
import im.yixin.nas.sdk.const.YXNasConstants
import im.yixin.nas.sdk.entity.UserInfo

/**
 * Created by jixia.cai on 2021/3/1 7:49 PM
 */
class UserInfoFragment : Fragment(R.layout.nas_demo_fragment_user_info) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) initViews(view)
        return view
    }

    private fun initViews(view: View) {
        val btn_use_info = view.findViewById<View>(R.id.btn_use_info)
        val btn_login_status = view.findViewById<View>(R.id.btn_login_status)
        val btn_quit = view.findViewById<View>(R.id.btn_quit)
        val tv_user_mobile = view.findViewById<TextView>(R.id.tv_user_mobile)
        var info = NasInvocationProxy.instance.getCurrentUserInfo()
        if (info != null && info.mobile.isNotEmpty()) {
            tv_user_mobile.text = info.mobile
            tv_user_mobile.visibility = View.VISIBLE
        } else {
            tv_user_mobile.visibility = View.GONE
        }

        btn_use_info.setOnClickListener {
            showLoading()
            YXNasSDK.instance.getTestApi().requestUserInfo(object : INasInvokeCallback<UserInfo> {

                override fun onResult(code: Int, message: String?, data: UserInfo?) {
                    Log.i(
                        NasDemoApp.TAG,
                        "request-userInfo result code: $code, message: $message, data: $data ~"
                    )
                    hideLoading()
                    if (code == YXNasConstants.ResultCode.CODE_SUCCESS) {
                        ToastUtil.showToast(context!!, "当前用户信息: $data")
                    } else {
                        ToastUtil.showToast(context!!, "获取用户信息失败: code:$code, message:$message")
                    }
                }

            })
        }

        val currentUserInfo = NasInvocationProxy.instance.getCurrentUserInfo()

        btn_login_status.setOnClickListener {
            showLoading()
            YXNasSDK.instance.getTestApi().requestLoginStatus(object : INasInvokeCallback<Boolean> {

                override fun onResult(code: Int, message: String?, data: Boolean?) {
                    Log.i(
                        NasDemoApp.TAG,
                        "request-loginStatus result code: $code, message: $message, data: $data ~"
                    )
                    hideLoading()
                    if (code == YXNasConstants.ResultCode.CODE_SUCCESS && data is Boolean) {
                        ToastUtil.showToast(context!!, if (data) "当前已登录" else "当前未登录")
                    } else {
                        ToastUtil.showToast(context!!, "获取用户登录信息失败: code:$code, message:$message")
                    }
                }

            })
        }

        btn_quit.setOnClickListener {
            showLoading()
            NasInvocationProxy.instance.execUserLogout(object : INasInvokeCallback<Void> {
                override fun onResult(code: Int, message: String?, data: Void?) {
                    Log.i(NasDemoApp.TAG, "logout result code: $code, message: $message ~")
                    hideLoading()
                    //设置本地userInfo数据
                    NasInvocationProxy.instance.updateUserInfo(currentUserInfo.also {
                        it?.isLogin = false
                    })
                    NasInvocationProxy.instance.notifyUserAction(UserAction.logoutSuccess)
                }

            })
        }
    }

    private var _loadingDialog: KProgressHUD? = null

    private fun showLoading() {
        _loadingDialog = KProgressHUD.create(context!!).also {
            it.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            it.setCancellable(true)
            it.setDimAmount(0.5f)
        }.show()
    }

    private fun hideLoading() {
        _loadingDialog?.dismiss()
    }
}