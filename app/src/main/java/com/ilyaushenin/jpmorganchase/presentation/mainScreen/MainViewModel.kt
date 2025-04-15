package com.ilyaushenin.jpmorganchase.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyaushenin.jpmorganchase.domain.usecase.GetUserUseCase
import com.ilyaushenin.jpmorganchase.presentation.mainScreen.components.MainStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    // states
    private val _states: MutableStateFlow<MainStates> =
        MutableStateFlow(MainStates())
    val states = _states.asStateFlow()

    init {
        getUserData()
    }

    private fun getUserData(){
        viewModelScope.launch {
            try {
                _states.update {
                    it.copy(
                        loading = true
                    )
                }

                delay(2000)
                val user = getUserUseCase.execute()

                _states.update { it.copy(user = user) }

                _states.update {
                    it.copy(
                        user = user
                    )
                }
                _states.update {
                    it.copy(
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _states.update {
                    it.copy(
                        loading = false
                    )
                }
                Log.d("error-in-MainViewModel", "$e")
            }
        }
    }
}