package com.example.pokemonapi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapi.adapter.ItemAdapter
import com.example.pokemonapi.databinding.ActivityMainBinding
import com.example.pokemonapi.models.PokeResponse
import com.example.pokemonapi.models.Pokemon
import com.example.pokemonapi.network.PokeManager
import com.example.pokemonapi.network.PokeService
import com.example.pokemonapi.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private var binding: ActivityMainBinding? = null

private var pokemonList: List<Pokemon>? = null


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        PokeManager(this).getResponse()

    }

    fun setupListOfDataIntoRecyclerView(pokemonList: List<Pokemon>) {
        if(pokemonList.isNotEmpty()){
        // Set the LayoutManager that this RecyclerView will use.
        binding?.rvItemsList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.rvItemsList?.adapter = ItemAdapter(pokemonList)
        binding?.rvItemsList?.visibility = View.VISIBLE
        binding?.tvNoRecordsAvailable?.visibility = View.GONE
        } else {
            binding?.rvItemsList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }

}

