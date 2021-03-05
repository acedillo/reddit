package com.cedillo.reddit.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cedillo.reddit.R
import com.cedillo.reddit.model.Data
import com.cedillo.reddit.util.ImageLoader

class PostFragment : Fragment() {

    companion object {
        private const val ARG_POST = "arg.post"
        fun newInstance(data: Data): PostFragment {
            val fragment = PostFragment()
            val args = Bundle()
            args.putParcelable(ARG_POST, data)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        val data = requireArguments()[ARG_POST] as Data
        data.selftext_html?.let {
            view.findViewById<TextView>(R.id.postText).text = Html.fromHtml(data.selftext_html)
        }

        view.findViewById<TextView>(R.id.postTitle).text = data.title
        ImageLoader.loadImage(view.findViewById(R.id.postImage), data.url)
        return view
    }
}