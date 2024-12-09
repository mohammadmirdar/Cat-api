package com.mirdar.catapiapp.ui.image_detail

import com.mirdar.catapiapp.domain.model.ImageDetail
import com.vapebothq.vendingmachineapp.data.common.Result

fun Result<ImageDetail?>.reduce(imageDetailState: ImageDetailState): ImageDetailState {
    return when (this) {
        is Result.Error -> imageDetailState.copy(
            isLoading = false,
            error = exception.toString(),
            imageDetail = ImageDetail.EMPTY
        )

        is Result.Loading -> imageDetailState.copy(
            isLoading = true,
            error = "",
            imageDetail = ImageDetail.EMPTY
        )

        is Result.Success -> imageDetailState.copy(
            isLoading = false,
            error = "",
            imageDetail = data ?: ImageDetail.EMPTY
        )
    }
}