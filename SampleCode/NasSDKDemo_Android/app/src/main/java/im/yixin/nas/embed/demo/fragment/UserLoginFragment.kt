package im.yixin.nas.embed.demo.fragment

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.impl.DemoUserInfo
import im.yixin.nas.embed.demo.impl.NasInvocationProxy
import im.yixin.nas.embed.demo.impl.UserAction
import im.yixin.nas.embed.demo.util.ToastUtil

/**
 * Created by jixia.cai on 2021/3/1 8:14 PM
 */
class UserLoginFragment : Fragment(R.layout.nas_demo_fragment_auth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
    }

    private fun findViews(view: View) {
        val et_token = view.findViewById<EditText>(R.id.et_token)
        val btn_auth = view.findViewById<View>(R.id.btn_auth)
        val current = NasInvocationProxy.instance.getCurrentUserInfo()
        if (current != null) {
            et_token.setText(current.token)
            et_token.requestFocus()
            et_token.setSelection(current.token?.length ?: 0)
        }

        btn_auth.setOnClickListener {
            val token = et_token.text?.toString()
            if (token.isNullOrEmpty()) {
                ToastUtil.showToast(requireContext(), "token不能为空")
                return@setOnClickListener
            }
            //保存用户数据
            NasInvocationProxy.instance.updateUserInfo(DemoUserInfo(true, token))
            NasInvocationProxy.instance.notifyUserAction(UserAction.authSuccess)
        }
    }
}