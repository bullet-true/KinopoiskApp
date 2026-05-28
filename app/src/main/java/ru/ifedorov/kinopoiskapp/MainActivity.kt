package ru.ifedorov.kinopoiskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ru.ifedorov.kinopoiskapp.app.KinopoiskApp
import ru.ifedorov.kinopoiskapp.ui.theme.KinopoiskAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KinopoiskAppTheme {
                KinopoiskApp()
            }
        }
    }
}