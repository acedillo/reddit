package com.cedillo.reddit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cedillo.reddit.CategoryAdapter
import com.cedillo.reddit.HomeViewModel
import com.cedillo.reddit.R
import com.cedillo.reddit.RetrofitRepository
import com.cedillo.reddit.model.Data

class CategoryFragment : Fragment() {

    private var viewModel: HomeViewModel? = null
    private var list: RecyclerView? = null

    companion object {

        private const val ARG_DATA_LIST = "arg.data.list"

        fun newInstance(dataList: List<Data>): CategoryFragment {
            val categoryFragment = CategoryFragment()
            val args = Bundle()

            args.putParcelableArrayList(ARG_DATA_LIST, ArrayList(dataList))
            categoryFragment.arguments = args
            return categoryFragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!, HomeViewModel.getFactory(RetrofitRepository()))
            .get(HomeViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        list = view.findViewById(R.id.list)
        list?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        list?.adapter = CategoryAdapter(arguments!![ARG_DATA_LIST] as ArrayList<Data>,
            object : CategoryAdapter.Listener {
                override fun onItemSelected(data: Data) {
                    viewModel?.onPostSelected(data)
                }
            })
        return view
    }
}
