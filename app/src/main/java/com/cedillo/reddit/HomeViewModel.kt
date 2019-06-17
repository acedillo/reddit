package com.cedillo.reddit

import androidx.lifecycle.*
import com.cedillo.reddit.model.Data
import kotlinx.coroutines.*

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private var coroutineScope : CoroutineScope = viewModelScope

    constructor(repository: Repository, coroutineScope : CoroutineScope) : this(repository){
        this.coroutineScope = coroutineScope
    }



    private val _loading = MutableLiveData<Boolean>()
    private val _mainRedditList = MutableLiveData<List<Data>>()
    private val _subRedditList = MutableLiveData<List<Data>>()
    private val _post = MutableLiveData<Data>()
    private val _notFound = MutableLiveData<Boolean>()

    val loading : LiveData<Boolean>
    get() = _loading

    val mainRedditList : LiveData<List<Data>>
    get() = _mainRedditList

    val subRedditList : LiveData<List<Data>>
    get() = _subRedditList

    val post : LiveData<Data>
    get() = _post

    val notFound : LiveData<Boolean>
    get() = _notFound


    companion object {
        fun getFactory(repository: Repository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            }
        }
    }

    fun getMain() : Job {
        _loading.postValue(true)
        return coroutineScope.launch {
            loadMain()
        }
    }

    fun onPostSelected(data: Data) {
        _post.postValue(data)
    }

    fun onSearchClick(subReddit: String) : Job? {
      return getSubReddit(subReddit)
    }

    private fun getSubReddit(subReddit : String) : Job?{

        if(subReddit.isBlank()){
            return null
        }
        _loading.postValue(true)
       return coroutineScope.launch {
           loadSubreddit(subReddit)
       }
    }

    private suspend fun loadMain(){

        return withContext(Dispatchers.IO) {
            val mainReddit = repository.getMainReddit()
            val list = mainReddit.data?.children?.map { it.data!! }
            _mainRedditList.postValue(list)
            _loading.postValue(false)

        }
    }

    private suspend fun loadSubreddit(subReddit: String){

        return withContext(Dispatchers.IO) {
            val mainReddit = repository.getSubreddit(subReddit)
            val list = mainReddit.data?.children?.map { it.data!! }
            if(list!!.isEmpty()){
                _notFound.postValue(true)
            }else {
                _subRedditList.postValue(list!!)
            }
            _loading.postValue(false)

        }
    }

}