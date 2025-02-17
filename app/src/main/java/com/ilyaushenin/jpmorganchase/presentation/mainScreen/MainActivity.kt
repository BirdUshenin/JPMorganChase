package com.ilyaushenin.jpmorganchase.presentation.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ilyaushenin.jpmorganchase.presentation.theme.JPMorganChaseTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val states by viewModel.states.collectAsState()
            JPMorganChaseTheme {
                MainScreen(
                    states = states
                )
            }
        }
        viewModel.getUserData()
    }
}