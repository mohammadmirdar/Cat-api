package com.mirdar.catapiapp.ui.image_list

import com.mirdar.catapiapp.domain.model.CatImage
import com.vapebothq.vendingmachineapp.data.common.Result

fun Result<List<CatImage>>.reduce(imageListState: ImageListState): ImageListState {
    return when (this) {
        is Result.Error -> imageListState.copy(isLoading = false, error = this.exception.toString())
        is Result.Loading -> imageListState.copy(isLoading = true)
        is Result.Success -> imageListState.copy(isLoading = false, imageList = this.data)
    }
}