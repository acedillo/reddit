package com.cedillo.reddit.rest

import com.cedillo.reddit.model.Main
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET(".json")
    fun getMainPosts(): Call<Main>

    @GET("r/{subreddit}/.json")
    fun getSubReddit(@Path("subreddit") subReddit: String): Call<Main>
}