package com.mexicandeveloper.sparqpokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mexicandeveloper.sparqpokemon.feature_pokemon.ui.PokemonScreen
import com.mexicandeveloper.sparqpokemon.ui.theme.SparqPokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SparqPokemonTheme {
                PokemonScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

