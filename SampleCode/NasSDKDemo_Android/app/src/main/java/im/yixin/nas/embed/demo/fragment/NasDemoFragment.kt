package im.yixin.nas.embed.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import im.yixin.nas.embed.demo.R
import im.yixin.nas.sdk.YXNasSDK

/**
 * Created by jixia.cai on 2021/6/24 11:58 上午
 */
class NasDemoFragment : Fragment(R.layout.nas_demo_fragment_nas_demo) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            val btn_open_nas_page = view.findViewById<View>(R.id.btn_open_nas_page)
            btn_open_nas_page.setOnClickListener {
                startActivity(YXNasSDK.instance.obtainFlutterIntent())
            }
        }
        return view
    }
}