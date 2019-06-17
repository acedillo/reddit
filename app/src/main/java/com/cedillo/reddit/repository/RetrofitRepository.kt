package com.cedillo.reddit.repository

import com.cedillo.reddit.model.Main
import com.cedillo.reddit.rest.ApiClient
import com.cedillo.reddit.rest.ApiInterface


class RetrofitRepository : Repository {

    override fun getSubreddit(subReddit: String): Main {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getSubReddit(subReddit)
        val response = call.execute()
        return response.body()
    }

    override fun getMainReddit(): Main {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getMainPosts()
        val response = call.execute()
        return response.body()
    }
}