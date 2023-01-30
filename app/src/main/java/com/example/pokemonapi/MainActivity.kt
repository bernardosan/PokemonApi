package com.example.pokemonapi


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapi.adapter.ItemAdapter
import com.example.pokemonapi.databinding.ActivityMainBinding
import com.example.pokemonapi.models.Pokemon
import com.example.pokemonapi.network.PokeManager

private var binding: ActivityMainBinding? = null


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

