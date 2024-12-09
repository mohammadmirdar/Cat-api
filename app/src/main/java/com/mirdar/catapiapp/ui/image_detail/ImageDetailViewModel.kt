package com.mirdar.catapiapp.ui.image_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mirdar.catapiapp.domain.GetImageDetail
import com.mirdar.catapiapp.domain.SetImageDetailFavourite
import com.mirdar.catapiapp.ui.ImageDetailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getImageDetail: GetImageDetail,
    private val setImageDetailFavourite: SetImageDetailFavourite
) : ViewModel() {

    private val _state = MutableStateFlow(ImageDetailState())
    val state: StateFlow<ImageDetailState> = _state.asStateFlow()

    init {
        val imageDetailItem = stateHandle.toRoute<ImageDetailItem>()
        handleIntent(ImageDetailIntent.GetImageDetail(imageDetailItem.imageId))
    }

    fun handleIntent(imageDetailIntent: ImageDetailIntent) {
        when (imageDetailIntent) {
            is ImageDetailIntent.GetImageDetail -> fetchImageDetail(imageDetailIntent.imageId)
            is ImageDetailIntent.MakeImageFavourite -> makeImageFavourite(
                imageDetailIntent.imageId,
                imageDetailIntent.isFavourite
            )
        }
    }

    private fun makeImageFavourite(imageId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            setImageDetailFavourite(imageId, isFavourite).collect { res ->
                _state.update { res.reduce(_state.value) }
            }
        }
    }

    private fun fetchImageDetail(imageId: String) {
        viewModelScope.launch {
            getImageDetail(imageId).collect { res ->
                _state.update { res.reduce(_state.value) }
            }
        }
    }
}