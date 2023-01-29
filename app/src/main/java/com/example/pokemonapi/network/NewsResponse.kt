package com.example.pokemonapi.network

import com.example.pokemonapi.models.Pokemon
import java.io.Serializable

data class NewsResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val articles: List<Pokemon>? = null,
    ) : Serializable
