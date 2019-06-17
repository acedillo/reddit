package com.cedillo.reddit.repository

import com.cedillo.reddit.model.Main

interface Repository {

    fun getMainReddit(): Main

    fun getSubreddit(subReddit: String): Main
}