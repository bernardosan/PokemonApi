package com.example.pokemonapi.network

import android.util.Log
import com.example.pokemonapi.models.ArticleCategory
import com.example.pokemonapi.models.getCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsManager {
    private var _newsResponse =  NewsResponse()
    fun getNewsResponse(): NewsResponse {
        return _newsResponse
    }

    private var _getArticleByCategory = NewsResponse()
    fun getArticleByCategory(): NewsResponse {
        return _getArticleByCategory
    }

    private var _getArticleBySource = NewsResponse()
    fun getArticleBySource(): NewsResponse {
        return _getArticleBySource
    }


    var selectedCategory: ArticleCategory? = ArticleCategory.GENERAL


    init {
        getArticles()
    }

    private fun getArticles(){
        val service = API.service.getNews("pt")
        service.enqueue(object: Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful){
                    _newsResponse = response.body()!!
                } else{
                    Log.d("error", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getArticlesByCategory(categoryName: String){
        val service = API.service.getArticlesByCategory(category = categoryName)
        service.enqueue(object: Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful){
                    _getArticleByCategory = response.body()!!
                } else{
                    Log.d("error", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getArticlesBySource(sourceName: String){
        val service = API.service.getArticlesByCategory(sourceName)
        service.enqueue(object: Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful){
                    _getArticleBySource = response.body()!!
                } else{
                    Log.d("error", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun onSelectedCategoryChanged(categoryName: String){
        val newCategory = getCategory(categoryName)
        selectedCategory = newCategory

    }
}