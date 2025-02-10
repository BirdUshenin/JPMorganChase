package com.ilyaushenin.jpmorganchase.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyaushenin.jpmorganchase.domain.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    // states
    val _states: MutableStateFlow<MainStates> =
        MutableStateFlow(MainStates())
    val states = _states.asStateFlow()

    fun getUserData(){
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getUser()
                _states.update {
                    it.copy(
                        user = response.user
                    )
                }
            } catch (e: Exception) {
                Log.d("errrrrrrr", "${e}}")
            }
        }
    }
}