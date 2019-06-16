package com.cedillo.reddit.util

import com.cedillo.reddit.model.Data

object PostUtil {
    fun getPost(list : List<Data>, permalink : String) : Data?{
        list.forEach{
            if(permalink.equals(it.permalink)){
                return it
            }
        }
        return null
    }
}