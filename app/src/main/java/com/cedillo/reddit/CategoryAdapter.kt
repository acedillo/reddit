package com.cedillo.reddit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cedillo.reddit.model.Data
import com.cedillo.reddit.util.ImageLoader

class CategoryAdapter(private val list: List<Data>, private val listener: Listener?) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = list[position]
        holder.category.text = data.subreddit
        holder.title.text = data.title
        if (data.thumbnail == null || data.thumbnail!!.isEmpty()) {
            holder.imageView.visibility = View.GONE
        } else {
            holder.imageView.visibility = View.VISIBLE
            ImageLoader.loadImage(holder.imageView, data.thumbnail)
        }
        holder.itemView.setOnClickListener { listener?.onItemSelected(data) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_post_row, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.postRowTitle)
        val category: TextView = itemView.findViewById(R.id.postRowCategory)
        val imageView: ImageView = itemView.findViewById(R.id.postRowImg)
    }

    interface Listener {
        fun onItemSelected(data: Data)
    }
}