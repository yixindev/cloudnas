package im.yixin.nas.embed.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.kaopiz.kprogresshud.KProgressHUD
import im.yixin.nas.embed.demo.NasDemoApp
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.impl.DemoUserInfo
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.embed.demo.impl.UserAction
import im.yixin.nas.embed.demo.util.ToastUtil
import im.yixin.nas.sdk.api.INasInvokeCallback
import im.yixin.nas.sdk.const.YXNasConstants
import java.util.regex.Pattern

/**
 * Created by jixia.cai on 2021/3/1 8:14 PM
 */
class UserLoginFragment : Fragment(R.layout.nas_demo_fragment_auth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
    }

    private fun findViews(view: View) {
        val et_mobile = view.findViewById<EditText>(R.id.et_mobile)
        val btn_auth = view.findViewById<View>(R.id.btn_auth)
        val current = NasInvocationProxy.instance.getCurrentUserInfo()
        if (current != null) {
            et_mobile.setText(current.mobile)
            et_mobile.requestFocus()
            et_mobile.setSelection(current.mobile.length)
        }

        btn_auth.setOnClickListener {
            val mobile = et_mobile.text?.toString()
            if (mobile.isNullOrEmpty()) {
                ToastUtil.showToast(context!!, "手机号不能为空")
                return@setOnClickListener
            }
            if (!verifyMobile(mobile)) {
                ToastUtil.showToast(context!!, "手机号格式非法，请重新输入")
                return@setOnClickListener
            }
            //保存用户数据
            NasInvocationProxy.instance.updateUserInfo(DemoUserInfo(mobile, true))
            NasInvocationProxy.instance.notifyUserAction(UserAction.authSuccess)
            startAuthLogin(mobile)
        }
    }

    private fun startAuthLogin(mobile: String) {
        NasInvocationProxy.instance.execUserLogin(mobile, object : INasInvokeCallback<Void> {

            override fun onResult(code: Int, message: String?, data: Void?) {
                Log.i(NasDemoApp.TAG, "auth result code: $code, message: $message ~")
                if (code == YXNasConstants.ResultCode.CODE_SUCCESS) {
                    ToastUtil.showToast(context ?: NasDemoApp.sContext, "授权登录成功 ~")
                } else {
                    ToastUtil.showToast(
                        context ?: NasDemoApp.sContext,
                        "授权登录失败 >> code: $code, message: $message ~"
                    )
                }
            }

        })
    }

    private fun verifyMobile(mobile: String?): Boolean {
        val pattern = Pattern.compile("^1[0-9]\\d{9}$")
        return pattern.matcher(mobile).matches()
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