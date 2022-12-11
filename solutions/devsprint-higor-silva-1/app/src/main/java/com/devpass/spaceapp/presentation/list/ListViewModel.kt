package com.devpass.spaceapp.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpass.spaceapp.models.Launch
import com.devpass.spaceapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class ListViewModel(private val repository: Repository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val launchList = MutableLiveData<List<Launch>>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        getLaunchs()
    }

     fun getLaunchs() {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.Main.immediate) {
            val response = repository.getLaunchs()

            if (response.isSuccessful) {
                launchList.postValue(response.body())
                isLoading.value = false
            } else {
                onError("Error: ${response.message()}")
            }
        }
    }

    private fun onError(msgError: String) {
        errorMessage.value = msgError
        isLoading.value = false
    }

}