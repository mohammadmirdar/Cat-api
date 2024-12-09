package com.mirdar.catapiapp.data.remote

import com.mirdar.catapiapp.data.remote.model.toDomain
import com.mirdar.catapiapp.domain.model.CatImage
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.mirdar.catapiapp.data.remote.common.CallErrors
import com.mirdar.catapiapp.data.remote.common.Result
import com.mirdar.catapiapp.data.remote.common.applyCommonSideEffects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val catApiService: CatApiService
) : RemoteRepository {

    override fun getAllImages(): Flow<Result<List<CatImage>>> = flow {
        catApiService.getImages().run {
            if (this.isSuccessful) {
                if (this.body() == null) {
                    emit(Result.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(Result.Success(this.body()!!.map { it.toDomain() }))
                }
            } else {
                emit(Result.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects()

    override fun getImageDetail(imageId: String): Flow<Result<ImageDetail>> = flow {
        catApiService.getImageDetail(imageId).run {
            if (this.isSuccessful) {
                if (this.body() == null) {
                    emit(Result.Error(CallErrors.ErrorEmptyData))
                } else {
                    emit(Result.Success(this.body()!!.toDomain()))
                }
            } else {
                emit(Result.Error(CallErrors.ErrorServer))
            }
        }
    }.applyCommonSideEffects()

}