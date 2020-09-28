package com.goldouble.android.memoment.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.memoment.R
import kotlinx.android.synthetic.main.activity_write.view.*

class VerticalSpaceItemDecoration(context: Context, private val slideOffset: Float) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val offset = 200

        if(parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?:1 - 1) {
            outRect.bottom = (slideOffset * offset).toInt()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        parent.dateText?.let {
            val left = it.paddingLeft + it.width / 2
            val right = left + 3

            for (i in 0 until parent.childCount - 1) {
                val child = parent.getChildAt(i)

                val top = child.top + child.dateText.bottom
                val bottom = parent.getChildAt(i + 1).top

                ContextCompat.getDrawable(parent.context, R.drawable.moment_divider)!!.apply {
                    setBounds(left, top, right, bottom)
                    alpha = (slideOffset * 255).toInt()
                    draw(c)
                }
            }
        }
    }
}