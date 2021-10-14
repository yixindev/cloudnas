package im.yixin.nas.embed.demo.util

import android.content.Context
import android.widget.Toast

/**
 * Created by jixia.cai on 2021/3/1 8:40 PM
 */
object ToastUtil {

    fun showToast(context: Context, message: String?) {
        if (!message.isNullOrEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}