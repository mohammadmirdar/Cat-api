package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.remote.RemoteRepository
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.mirdar.catapiapp.data.remote.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetImageDetail @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {
    operator fun invoke(imageId: String): Flow<Result<ImageDetail>> = flow {
        localRepository.readImageDetail(imageId)?.let { imageDetail ->
            emit(Result.Success(imageDetail))
        } ?: run {
            remoteRepository.getImageDetail(imageId).collect { detailRes ->
                if (detailRes is Result.Success) {
                    localRepository.insertImageDetail(detailRes.data)
                    emit(Result.Success(localRepository.readImageDetail(imageId)!!))
                } else {
                    emit(detailRes)
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}