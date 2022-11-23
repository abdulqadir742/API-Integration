package com.example.implement_api

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.implement_api.databinding.ItemLayoutBinding

class NewsAdapter(val context: Context, val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ArticleViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.newsTitle.text = article.title
        holder.newsDescription.text = article.description
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener {
            Toast.makeText(context, article.title, Toast.LENGTH_SHORT).show()
        }

    }


    override fun getItemCount(): Int {

        return articles.size
    }

    class ArticleViewHolder(binding: ItemLayoutBinding) : ViewHolder(binding.root) {
        var newsImage = binding.newsImage
        var newsTitle = binding.newsTitle
        var newsDescription = binding.newsDescription
    }

}