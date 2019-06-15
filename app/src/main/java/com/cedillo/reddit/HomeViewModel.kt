package com.cedillo.reddit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cedillo.reddit.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

class HomeViewModel(val repository: Repository) : ViewModel() {

    private val _spinner = MutableLiveData<Boolean>()
    private val _dataList = MutableLiveData<List<Data>>()

    val spinner : LiveData<Boolean>
    get() = _spinner

    val dataList : LiveData<List<Data>>
    get() = _dataList

    fun getMain() {
        viewModelScope.launch {
            loadMain()
        }
    }

    fun getSubReddit(subReddit : String){
       viewModelScope.launch {
           loadSubreddit(subReddit)
       }
    }

    suspend fun loadMain(){
        return withContext(Dispatchers.IO) {
            val mainReddit = repository.getMainReddit()
            val list = mainReddit.data?.children?.map { it.data!! }
            _dataList.postValue(list)

        }
    }

    suspend fun loadSubreddit(subReddit: String) : List<Data>{
        return suspendCoroutine {
            val mainReddit = repository.getSubreddit(subReddit)
            val list = mainReddit.data?.children?.map { it.data!! }
            _dataList.postValue(list!!)

        }
    }


}