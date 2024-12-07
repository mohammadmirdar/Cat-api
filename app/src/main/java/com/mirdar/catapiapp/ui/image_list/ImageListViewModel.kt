package com.mirdar.catapiapp.ui.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor() : ViewModel(){

    private val _state = MutableStateFlow(ImageListState())
    val state : StateFlow<ImageListState> = _state.asStateFlow()

    fun handleIntent(imageListIntent: ImageListIntent) {
        when (imageListIntent) {
            ImageListIntent.LoadImageList -> loadImageList()
        }
    }

    private fun loadImageList() {
        viewModelScope.launch {

        }
    }
}