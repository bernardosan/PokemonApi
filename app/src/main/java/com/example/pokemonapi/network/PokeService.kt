package com.example.pokemonapi.network

import com.example.pokemonapi.models.PokeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeService {

    @GET("pokemon")
    fun getPokemon(
        @Query("limit") limit: Int = 100000,
    ): Call<PokeResponse>

}
// END