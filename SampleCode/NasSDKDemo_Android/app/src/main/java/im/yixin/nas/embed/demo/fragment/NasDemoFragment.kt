package im.yixin.nas.embed.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import im.yixin.nas.embed.demo.NasDemoApp
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.embed.demo.util.ToastUtil
import im.yixin.nas.sdk.YXNasSDK
import im.yixin.nas.sdk.api.INasInvokeCallback

/**
 * Created by jixia.cai on 2021/6/24 11:58 上午
 */
class NasDemoFragment : Fragment(R.layout.nas_demo_fragment_nas_demo) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            val btn_open_nas_page = view.findViewById<View>(R.id.btn_open_nas_page)
            btn_open_nas_page.setOnClickListener {
                val current = NasInvocationProxy.instance.getCurrentUserInfo()
                if (current != null && current.isLogin == true && !current.token.isNullOrEmpty()) {
                    NasInvocationProxy.instance.execUserLogin(
                        current.token,
                        object : INasInvokeCallback<Void> {
                            override fun onResult(code: Int, message: String?, data: Void?) {
                                Log.i(
                                    NasDemoApp.TAG,
                                    "nas-sdk auth result code: $code, message: $message ~"
                                )
                                if (code == 200) {
                                    startActivity(
                                        YXNasSDK.instance.obtainFlutterIntent()
                                    )
                                } else if (code == 4001) {
                                    ToastUtil.showToast(context!!, "用户激活失败，请先激活用户")
                                } else {
                                    ToastUtil.showToast(context!!, "授权失败，请稍后再试")
                                }
                            }
                        }
                    )
                }

            }
        }
        return view
    }
}