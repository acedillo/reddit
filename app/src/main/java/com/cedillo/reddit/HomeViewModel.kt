package com.cedillo.reddit

import androidx.lifecycle.*
import com.cedillo.reddit.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(val repository: Repository) : ViewModel() {

    private val _spinner = MutableLiveData<Boolean>()
    private val _mainRedditList = MutableLiveData<List<Data>>()
    private val _subRedditList = MutableLiveData<List<Data>>()
    private val _post = MutableLiveData<Data>()


    val spinner : LiveData<Boolean>
    get() = _spinner

    val mainRedditList : LiveData<List<Data>>
    get() = _mainRedditList

    val subRedditList : LiveData<List<Data>>
    get() = _subRedditList

    val post : LiveData<Data>
    get() = _post


    companion object {
        fun getFactory(repository: Repository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            }
        }
    }

    fun getMain() {
        viewModelScope.launch {
            loadMain()
        }
    }

    fun getSubReddit(subReddit : String){
        if(subReddit.isBlank()){
            return
        }
       viewModelScope.launch {
           loadSubreddit(subReddit)
       }
    }

    suspend fun loadMain(){
        return withContext(Dispatchers.IO) {
            val mainReddit = repository.getMainReddit()
            val list = mainReddit.data?.children?.map { it.data!! }
            _mainRedditList.postValue(list)

        }
    }

    suspend fun loadSubreddit(subReddit: String){
        return withContext(Dispatchers.IO) {
            val mainReddit = repository.getSubreddit(subReddit)
            val list = mainReddit.data?.children?.map { it.data!! }
            _subRedditList.postValue(list!!)

        }
    }

    fun onPostSelected(data: Data) {
        _post.postValue(data)
    }

    fun onSearchClick(subReddit: String) {
        getSubReddit(subReddit)
    }


}