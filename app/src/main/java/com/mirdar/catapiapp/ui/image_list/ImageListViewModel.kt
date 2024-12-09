package com.mirdar.catapiapp.ui.image_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirdar.catapiapp.domain.GetImageList
import com.mirdar.catapiapp.domain.SetImageFavourite
import com.mirdar.catapiapp.domain.model.CatImage
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
    private val setImageFavourite: SetImageFavourite
) : ViewModel() {

    private val _state = MutableStateFlow(ImageListState())
    val state: StateFlow<ImageListState> = _state.asStateFlow()

    private val imageList = mutableListOf<CatImage>()

    fun handleIntent(imageListIntent: ImageListIntent) {
        when (imageListIntent) {
            is ImageListIntent.LoadImageList -> loadImageList()
            is ImageListIntent.SetImageFavourite -> setFavourite(
                imageListIntent.imageId,
                imageListIntent.isFavourite
            )
        }
    }

    private fun setFavourite(imageId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            setImageFavourite(imageId, isFavourite).collect { res ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "",
                        imageList.map {
                            if (it.id == res.data.id) it.copy(isFavourite = res.data.isFavourite) else it
                        })
                }
            }
        }
    }

    private fun loadImageList() {
        viewModelScope.launch {
            getImageList().collect { res ->
                _state.update { res.reduce(_state.value) }
                imageList.clear()
                imageList.addAll(_state.value.imageList)
            }
        }
    }

    fun consume() {
        _state.update { _state.value.copy(isLoading = false, error = "", imageList = emptyList()) }
    }
}