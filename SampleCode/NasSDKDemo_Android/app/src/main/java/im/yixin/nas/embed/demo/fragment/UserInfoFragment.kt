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
import im.yixin.nas.sdk.api.INasInvokeCallback

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
        val btn_quit = view.findViewById<View>(R.id.btn_quit)
        val tv_token = view.findViewById<TextView>(R.id.tv_token)
        var info = NasInvocationProxy.instance.getCurrentUserInfo()
        if (info != null && info.token?.isEmpty() == false) {
            tv_token.text = info.token
            tv_token.visibility = View.VISIBLE
        } else {
            tv_token.visibility = View.GONE
        }
        val currentUserInfo = NasInvocationProxy.instance.getCurrentUserInfo()
        btn_quit.setOnClickListener {
            showLoading()
            NasInvocationProxy.instance.execUserLogout(object : INasInvokeCallback<Void> {
                override fun onResult(code: Int, message: String?, data: Void?) {
                    Log.i(NasDemoApp.TAG, "logout result code: $code, message: $message ~")
                    hideLoading()
                    //设置本地userInfo数据
                    NasInvocationProxy.instance.updateUserInfo(currentUserInfo.also {
                        it?.isLogin = false
                        it?.token = null
                    })
                    NasInvocationProxy.instance.notifyUserAction(UserAction.logoutSuccess)
                }

            })
        }
    }

    private var _loadingDialog: KProgressHUD? = null

    private fun showLoading() {
        _loadingDialog = KProgressHUD.create(requireContext()).also {
            it.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            it.setCancellable(true)
            it.setDimAmount(0.5f)
        }.show()
    }

    private fun hideLoading() {
        _loadingDialog?.dismiss()
    }
}