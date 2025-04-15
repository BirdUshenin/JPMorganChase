package com.ilyaushenin.jpmorganchase.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyaushenin.jpmorganchase.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    init {
        getUserData()
    }

    // states
    private val _states: MutableStateFlow<MainStates> =
        MutableStateFlow(MainStates())
    val states = _states.asStateFlow()

    private fun getUserData(){
        viewModelScope.launch {
            try {
                val user = getUserUseCase.execute()

                _states.update { it.copy(user = user) }

                _states.update {
                    it.copy(
                        user = user
                    )
                }
            } catch (e: Exception) {
                Log.d("error-in-MainViewModel", "$e")
            }
        }
    }
}