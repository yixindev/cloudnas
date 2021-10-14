package im.yixin.nas.embed.demo.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import im.yixin.nas.embed.demo.R

/**
 * Created by jixia.cai on 2021/2/19 2:16 PM
 */
class MyTabView {

    companion object {

        fun obtainNew(
            icons: Array<Drawable>?,
            textColors: Array<Int>?,
            text: String?
        ): MyTabViewOpt {
            return MyTabViewOpt(
                icon = buildIcon(icons),
                textColor = buildColor(textColors),
                text = text
            )
        }

        private fun buildColor(colors: Array<Int>?): ColorStateList? {
            if (colors.isNullOrEmpty()) return null
            return _buildColor(colors[0], if (colors.size > 1) colors[1] else null)
        }

        private fun _buildColor(normal: Int, select: Int?): ColorStateList {
            return ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_selected),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected)
                ),
                intArrayOf(normal, select ?: normal)
            )
        }

        private fun buildIcon(icons: Array<Drawable>?): Drawable? {
            if (icons.isNullOrEmpty()) return null
            return _buildIcon(icons[0], if (icons.size > 1) icons[1] else null)
        }

        private fun _buildIcon(normal: Drawable, select: Drawable? = null): Drawable {
            return StateListDrawable().also {
                it.addState(
                    intArrayOf(
                        android.R.attr.state_enabled,
                        -android.R.attr.state_selected
                    ), normal
                )
                it.addState(
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
                    select ?: normal
                )
            }
        }
    }

    class MyTabViewOpt {

        private var icon: Drawable? = null

        private var text: String? = null

        private var textColor: ColorStateList? = null

        constructor(icon: Drawable?, textColor: ColorStateList?, text: String?) {
            this.icon = icon
            this.text = text
            this.textColor = textColor
        }

        fun apply(parent: TabLayout): TabLayout.Tab? {
            if (icon == null && text.isNullOrEmpty()) return null
            return parent.newTab().also {
                it.customView = updateView(context = parent.context)
            }
        }

        fun updateView(context: Context): View {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.nas_demo_view_tab_item, null)
            val iv_tab_item = view.findViewById<ImageView>(R.id.iv_tab_item)
            if (icon != null) {
                iv_tab_item.setImageDrawable(icon)
                iv_tab_item.visibility = View.VISIBLE
            } else {
                iv_tab_item.visibility = View.GONE
            }
            val tv_tab_item = view.findViewById<TextView>(R.id.tv_tab_item)
            if (text.isNullOrEmpty()) {
                tv_tab_item.visibility = View.GONE
            } else {
                tv_tab_item.visibility = View.VISIBLE
                tv_tab_item.text = text
            }
            if (textColor != null) {
                tv_tab_item.setTextColor(textColor)
            }
//            tv_tab_item.setTextColor(context.resources.getColorStateList(R.color.nas_demo_tab_textcolor))
            return view
        }

        fun applyCustomView(tab: TabLayout.Tab) {
            val context = tab.parent?.context ?: return
            tab.customView = updateView(context)
        }
    }
}