package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()

    var totalResults=-1
    var pageNum=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val newsList = findViewById<RecyclerView>(R.id.newsList)

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        //newsList.
        val layoutManager = LinearLayoutManager(this)

        newsList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
              // val lastVisibleItemPosition :Int =layoutManager.findLastVisibleItemPosition()
                val firstVisibleItemPosition : Int = layoutManager.findFirstVisibleItemPosition()

                   super.onScrolled(recyclerView, dx, dy)
               if (totalResults >layoutManager.itemCount && firstVisibleItemPosition >= layoutManager.itemCount -5)
               {
                   pageNum ++
                   getNews()
               }
           }

        })
        newsList.layoutManager= layoutManager
        getNews()
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadline("in", pageNum)


        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {

                    totalResults= news.totalResults

                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("JATIN", "Error In Fetching News", t)
            }
        })
    }
}
