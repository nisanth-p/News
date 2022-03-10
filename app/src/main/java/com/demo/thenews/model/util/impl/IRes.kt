package com.demo.thenews.model.util.impl

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface IRes {
    fun str(resId: Int): String
    fun color(resId: Int): Int
    fun drawable(resId: Int): Drawable?
}

class ResImpl @Inject constructor(@ApplicationContext private val context: Context): IRes {
    override fun str(resId: Int): String = context.getString(resId)

    override fun color(resId: Int): Int = ContextCompat.getColor(context, resId)

    override fun drawable(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)

}