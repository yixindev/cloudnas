package im.yixin.nas.embed.demo.impl

/**
 * Created by jixia.cai on 2021/3/3 7:34 PM
 */
data class DemoUserInfo(var isLogin: Boolean?, var token: String? = null) {

    fun withToken(userToken: String?): DemoUserInfo {
        this.token = userToken
        return this
    }
}