package com.example.newsapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

    //https://newsapi.org/v2/top-headlines?country=in&apiKey=
    //https://newsapi.org/v2/everything?q=apple&from=2022-12-15&to=2022-12-15&sortBy=popularity&apiKey
    //https://newsapi.org/v2/top-headlines?apiKey=23b51e74e1a741cfaf03f944184fdcb5&country=in&page=1

const val BASE_URL ="https://newsapi.org/"
const val API_KEY ="23b51e74e1a741cfaf03f944184fdcb5"

interface NewsInterface{

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadline(@Query("country") country:String,@Query("page") page:Int) : Call<News>

}

object  NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}