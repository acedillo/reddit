package com.cedillo.reddit.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    fun loadImage(view: ImageView, url: String?) {
        if (url == null || url.isEmpty()) {
            return
        }
        Glide.with(view)
            .load(url)
            .into(view)
    }
}