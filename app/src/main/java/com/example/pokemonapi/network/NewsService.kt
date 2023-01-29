package com.example.pokemonapi.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("pokemon")
    fun getPokemon(
        @Query("limit") limit: Int = 100000,
    ): Call<NewsResponse>

}
// END