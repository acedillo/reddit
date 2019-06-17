package com.cedillo.reddit

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cedillo.reddit.fragment.CategoryFragment
import com.cedillo.reddit.fragment.PostFragment
import com.cedillo.reddit.model.Data

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel =
            ViewModelProviders.of(this, HomeViewModel.getFactory(RetrofitRepository())).get(HomeViewModel::class.java)

        viewModel.mainRedditList.observe(this, Observer<List<Data>> {
            supportFragmentManager.beginTransaction()
                .replace(R.id.homeContentContainer, CategoryFragment.newInstance(it)).commit()
        })

        viewModel.post.observe(this, Observer {
            supportFragmentManager.beginTransaction().add(R.id.homeContentContainer, PostFragment.newInstance(it))
                .addToBackStack(PostFragment::class.java.name)
                .commit()
        })

        viewModel.subRedditList.observe(this, Observer<List<Data>> {
            supportFragmentManager.beginTransaction()
                .add(R.id.homeContentContainer, CategoryFragment.newInstance(it))
                .addToBackStack(CategoryFragment::class.java.name)
                .commit()
        })

        viewModel.loading.observe(this, Observer {
            findViewById<TextView>(R.id.homeLoading).visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.notFound.observe(this, Observer {
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_SHORT).show()
        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.getMain()

    }

    fun onSearchClick(v: View) {
        viewModel.onSearchClick(findViewById<EditText>(R.id.homeSearchText).text.toString())
    }


}
