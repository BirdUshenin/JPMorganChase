package com.ilyaushenin.jpmorganchase.presentation.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ilyaushenin.jpmorganchase.presentation.mainScreen.components.MainScreen
import com.ilyaushenin.jpmorganchase.presentation.theme.JPMorganChaseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

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
    }
}