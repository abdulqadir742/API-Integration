package com.example.implement_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.implement_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalresult = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        adapter = NewsAdapter(this@MainActivity,articles)
        binding.newsList.adapter = adapter
        val layoutManager1 = LinearLayoutManager(this@MainActivity)
        binding.newsList.layoutManager = layoutManager1
        supportActionBar?.hide()

       binding.newsList.addOnScrollListener(object : OnScrollListener(){
           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               if (totalresult > layoutManager1.itemCount && layoutManager1.findFirstVisibleItemPosition()>= layoutManager1.itemCount -7)
               {
                   pageNum++
                   getNews()
               }
               super.onScrolled(recyclerView, dx, dy)
           }
       })



        getNews()
    }

    private fun getNews() {
        val news = NewService.newsInstance.getHeadlines("in",pageNum)
        news.enqueue(object :retrofit2.Callback<News>{

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()
                if (news!= null)
                {
                    Log.d("MY News",news.toString())
                    totalresult = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("MY News","Error in Fetching News")
            }
        })
    }
}