package im.yixin.nas.embed.demo.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.base.FragmentHostActivity
import im.yixin.nas.embed.demo.fragment.UserInfoFragment
import im.yixin.nas.sdk.YXNasSDK

/**
 * Created by jixia.cai on 2021/3/1 7:33 PM
 */
@Deprecated("")
class DemoHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nas_demo_activity_home)
        initViews()
    }

    private fun initViews() {
        val btn_tab_mode = findViewById<View>(R.id.btn_tab_mode)
        val btn_entry_mode = findViewById<View>(R.id.btn_entry_mode)
        val btn_user_react = findViewById<View>(R.id.btn_user_react)
        btn_tab_mode.setOnClickListener {
            startActivity(Intent(DemoHomeActivity@ this, TabModeActivity::class.java))
        }
        btn_entry_mode.setOnClickListener {
            startActivity(YXNasSDK.instance.obtainFlutterIntent())
        }
        btn_user_react.setOnClickListener {
            startActivity(Intent(DemoHomeActivity@ this, FragmentHostActivity::class.java).apply {
                this.putExtra(
                    FragmentHostActivity.KEY_NAME_FRAGMENT,
                    UserInfoFragment::class.java.name
                )
            })
        }
    }
}