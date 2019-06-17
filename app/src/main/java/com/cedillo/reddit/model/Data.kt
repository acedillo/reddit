package com.cedillo.reddit.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    var subreddit: String? = null,
    var title: String? = null,
    var thumbnail: String? = null,
    var permalink: String? = null,
    var url: String? = null,
    var selftext_html: String? = null
) : Parcelable