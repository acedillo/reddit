package com.cedillo.reddit.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.cedillo.reddit.R


object ImageLoader {
    fun loadImage(view: ImageView, url: String?) {
        if (url == null || url.isEmpty()) {
            return
        }

        val options = RequestOptions()
            .placeholder(R.drawable.ic_android)
            .error(R.drawable.ic_android)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(view)
            .load(url)
            .apply(options)
            .into(view)

    }
}