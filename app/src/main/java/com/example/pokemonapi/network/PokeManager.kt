package com.example.pokemonapi.network

import android.app.Activity
import android.util.Log
import com.example.pokemonapi.MainActivity
import com.example.pokemonapi.models.PokeResponse
import com.example.pokemonapi.models.Pokemon
import com.example.pokemonapi.utils.Constants.BASE_URL
import com.example.pokemonapi.utils.Constants.isNetworkAvailable
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


 class PokeManager(val activity: Activity) {

    private var _pokeResponse = PokeResponse()
    private var _pokemonList: List<Pokemon>? = null
    fun getPokemonList(): List<Pokemon>? {
        return _pokemonList
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
    private val cache = Cache(activity.application.cacheDir, cacheSize)
    private val logging = HttpLoggingInterceptor()

    private var onlineInterceptor: Interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val response: okhttp3.Response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }
    }

    private var offlineInterceptor: Interceptor = object : Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request: Request = chain.request()
            if (!isNetworkAvailable(activity.applicationContext)) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            return chain.proceed(request)
        }
    }

    private val httpClient = OkHttpClient.Builder().apply {
        /*addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-key", API_KEY)
                return@Interceptor chain.proceed(builder.build())
            }
        )*/
        logging.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(offlineInterceptor)
        addNetworkInterceptor(logging)
        addNetworkInterceptor(onlineInterceptor)
        cache(cache)
    }.build()


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(httpClient)
        .build()

    private val service: PokeService = retrofit.create(PokeService::class.java)


    fun getResponse(){
        val service = service.getPokemon()
        service.enqueue(object: Callback<PokeResponse>{
            override fun onResponse(call: Call<PokeResponse>, response: Response<PokeResponse>){
                if(response.isSuccessful){
                    _pokeResponse = response.body()!!
                    _pokemonList = _pokeResponse.results
                    if(getPokemonList() != null && activity is MainActivity){
                        activity.setupListOfDataIntoRecyclerView(getPokemonList()!!)
                    }

                } else {
                    Log.d("error", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}