package im.yixin.nas.embed.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import im.yixin.nas.embed.demo.NasDemoApp
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.embed.demo.impl.OnNasSDKnitListener
import im.yixin.nas.embed.demo.impl.OnNasUserListener
import im.yixin.nas.sdk.api.INasInvokeCallback

/**
 * Created by jixia.cai on 2021/3/1 8:17 PM
 */
class EmptyFragment(private var title: String? = null, private var message: String? = null) :
    Fragment(R.layout.nas_demo_fragment_empty), OnNasSDKnitListener, OnNasUserListener {

    private var current: Fragment? = null

    private var tv_message: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        tv_message = view?.findViewById(R.id.tv_message)
        tv_message?.text = message ?: "我的页面"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NasInvocationProxy.instance.addNasSDKnitListener(this)
        NasInvocationProxy.instance.setNasUserListener(this)
        val current = NasInvocationProxy.instance.getCurrentUserInfo()
        if (current?.isLogin == true) {
            updateStatus(true)
        } else {
            updateStatus(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NasInvocationProxy.instance.removeNasSDKnitListener(this)
        NasInvocationProxy.instance.setNasUserListener(null)
    }

    private fun updateStatus(isLogin: Boolean) {
        Log.i(NasDemoApp.TAG, "update login-status: $isLogin ~")
        val transaction = fragmentManager?.beginTransaction() ?: return
        if (current != null) {
            transaction.remove(current!!)
        }
        tv_message?.visibility = View.GONE
        if (isLogin) {
            current = UserInfoFragment()
            transaction.add(R.id.ly_fragments_host, current!!)
        } else {
            current = UserLoginFragment()
            transaction.add(R.id.ly_fragments_host, current!!)
        }
        transaction.show(current!!)
        transaction.commitAllowingStateLoss()
    }

    override fun onSDKInitSuccess() {
        //判断当前是否已经登录
//        val current = NasInvocationProxy.instance.getCurrentUserInfo()
//        if (current != null && current.isLogin == true && !current.token.isNullOrEmpty()) {
//            NasInvocationProxy.instance.execUserLogin(
//                    current.token,
//                    object : INasInvokeCallback<Void> {
//                        override fun onResult(code: Int, message: String?, data: Void?) {
//                            Log.i(
//                                NasDemoApp.TAG,
//                                "nas-sdk auth result code: $code, message: $message ~"
//                            )
//                        }
//                    }
//                )
//        }
    }

    override fun onUserAuthSuccess() {
        updateStatus(true)
    }

    override fun onUserLogoutSuccess() {
        updateStatus(false)
    }
}