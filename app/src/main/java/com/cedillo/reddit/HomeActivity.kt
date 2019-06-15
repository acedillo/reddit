package com.cedillo.reddit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cedillo.reddit.model.Data

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
               return HomeViewModel(RetrofitRepository()) as T
            }

        }).get(HomeViewModel::class.java)

        viewModel.dataList.observe(this, Observer<List<Data>> {
            Log.d("TAG", "list ${it.size}")
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMain()

    }


}
