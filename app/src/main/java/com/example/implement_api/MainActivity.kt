package com.example.implement_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.implement_api.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        supportActionBar?.hide()
        getNews()
    }

    private fun getNews() {
        val news = NewService.newsInstance.getHeadlines("us",2)
        news.enqueue(object :retrofit2.Callback<News>{

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()
                if (news!= null)
                {
                    Log.d("MY News",news.toString())
                    adapter = NewsAdapter(this@MainActivity,news.articles)
                    binding.newsList.adapter = adapter
                    binding.newsList.layoutManager = LinearLayoutManager(this@MainActivity)

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("MY News","Error in Fetching News")
            }
        })
    }
}