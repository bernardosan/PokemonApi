package com.example.pokemonapi.models

import java.io.Serializable

data class PokeResponse(
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Pokemon>? = null,
    ) : Serializable
