package com.amir.notesapp.utils

import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

fun FragmentActivity.launchActivity(intent: Intent) {
    startActivity(intent)
}

fun Int.dpToPx(): Int {
    return this.toFloat().dpToPx()
}

fun Float.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).toInt()
}

fun RecyclerView.applyItemDecoration(left: Int, right: Int, top: Int, bottom: Int) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = left.dpToPx()
            outRect.right = right.dpToPx()
            outRect.top = top.dpToPx()
            outRect.bottom = bottom.dpToPx()

        }
    })
}