package com.example.classes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.classes.WebNew
import com.example.newsapp.R

class WebNewAdapter(
    var listWebNews: ArrayList<WebNew>,
    val onClickDelete: (Int) -> Unit,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<WebNewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View, onClickDelete: (Int) -> Unit, onClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.tvTitle) as TextView
        val subtitle = itemView.findViewById(R.id.tvSubtitle) as TextView
        val bussiness = itemView.findViewById(R.id.tvbussiness) as TextView
        val video = itemView.findViewById(R.id.tvVideo) as TextView
        val url = itemView.findViewById(R.id.tvUrl) as TextView
        val id = itemView.findViewById(R.id.tvId) as TextView
        val delete = itemView.findViewById<View>(R.id.btnDelete).setOnClickListener {
            onClickDelete(adapterPosition)
        }
        val click = itemView.setOnClickListener {
            onClickListener(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_news, parent, false)

        return ViewHolder(view, onClickDelete, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val webNew = listWebNews[position]

        holder.title.text = webNew.title
        holder.subtitle.text = webNew.subtitle
        holder.bussiness.text = webNew.bussiness
        holder.video.text = webNew.video
        holder.url.text = webNew.url
        holder.id.text = webNew.id.toString()
    }

    override fun getItemCount(): Int {
        return listWebNews.size
    }

    fun filter(listFiltered: ArrayList<WebNew>) {
        this.listWebNews = listFiltered
        this.notifyDataSetChanged()
    }
}