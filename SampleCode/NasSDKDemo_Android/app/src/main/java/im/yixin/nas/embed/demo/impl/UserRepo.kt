package im.yixin.nas.embed.demo.impl

import android.content.Context
import android.util.Log
import im.yixin.nas.embed.demo.NasDemoApp
import im.yixin.nas.embed.demo.util.PrefsUtil

/**
 * Created by jixia.cai on 2021/3/3 1:58 PM
 */
class UserRepo {

    companion object {

        private const val KEY_NAME_USER_INFO = "demo.key_user_info"
    }

    private var context: Context? = null

    private var current: DemoUserInfo? = null

    constructor(context: Context) {
        this.context = context
        current = PrefsUtil.read<DemoUserInfo>(context, KEY_NAME_USER_INFO, null)
        Log.i(NasDemoApp.TAG, "init userInfo : $current")
    }

    fun getCurrentInfo(): DemoUserInfo? = current

    fun saveUserInfo(userInfo: DemoUserInfo?) {
        Log.i(NasDemoApp.TAG, "save userInfo : $userInfo")
        PrefsUtil.save(context!!, KEY_NAME_USER_INFO, userInfo)
        current = userInfo
    }
}