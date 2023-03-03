package com.example.newsapp

data class News(val status:String,val totalResults:Int,val articles: List<Article>)