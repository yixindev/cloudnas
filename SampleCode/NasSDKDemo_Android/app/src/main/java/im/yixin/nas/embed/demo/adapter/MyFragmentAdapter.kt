package im.yixin.nas.embed.demo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by jixia.cai on 2021/2/19 11:43 AM
 */
class MyFragmentAdapter<T : Fragment> : FragmentStatePagerAdapter {

    private var fragments: ArrayList<T>

    constructor(fm: FragmentManager, fragments: ArrayList<T>) : super(fm) {
        this.fragments = fragments
    }

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}