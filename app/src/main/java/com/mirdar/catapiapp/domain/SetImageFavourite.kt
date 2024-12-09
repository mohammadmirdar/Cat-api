package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.vapebothq.vendingmachineapp.data.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SetImageFavourite @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(imageId: String, isFavourite: Boolean) = flow {
        localRepository.setImageFavourite(imageId, isFavourite)
        emit(Result.Success(localRepository.readImageList().first { it.id == imageId }))
    }.flowOn(Dispatchers.IO)
}