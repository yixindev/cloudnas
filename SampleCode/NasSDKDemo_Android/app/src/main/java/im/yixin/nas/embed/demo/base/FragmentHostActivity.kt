package im.yixin.nas.embed.demo.base

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import im.yixin.nas.embed.demo.R
import im.yixin.nas.embed.demo.fragment.EmptyFragment
import kotlin.math.tan

/**
 * Created by jixia.cai on 2021/3/1 7:58 PM
 */
class FragmentHostActivity : FragmentActivity() {

    companion object {

        const val KEY_NAME_FRAGMENT = "key_fragment_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nas_demo_activity_host)
        initFragment()
    }

    private fun initFragment() {
        val fragmentName = intent.getStringExtra(KEY_NAME_FRAGMENT)
        if (fragmentName != null) {
            val clazz = Class.forName(fragmentName)
            val instance = clazz.newInstance()
            if (instance != null && instance is Fragment) {
                showFragment(instance)
                return
            }
        }
        showFragment(EmptyFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val trans = supportFragmentManager.beginTransaction()
        trans.add(R.id.ly_fragments_host, fragment)
        trans.show(fragment)
        trans.commitAllowingStateLoss()
    }
}