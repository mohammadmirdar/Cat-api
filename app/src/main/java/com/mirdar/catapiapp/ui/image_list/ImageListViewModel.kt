package com.mirdar.catapiapp.ui.image_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirdar.catapiapp.domain.GetImageList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImageList: GetImageList,
) : ViewModel() {

    private val _state = MutableStateFlow(ImageListState())
    val state: StateFlow<ImageListState> = _state.asStateFlow()

    init {
        handleIntent(ImageListIntent.LoadImageList)
    }

    fun handleIntent(imageListIntent: ImageListIntent) {
        when (imageListIntent) {
            ImageListIntent.LoadImageList -> loadImageList()
        }
    }

    private fun loadImageList() {
        viewModelScope.launch {
            getImageList().collect { res ->
                _state.update { res.reduce(_state.value) }
            }
        }
    }
}