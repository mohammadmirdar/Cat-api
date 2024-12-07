package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.remote.RemoteRepository
import com.mirdar.catapiapp.domain.model.CatImage
import com.vapebothq.vendingmachineapp.data.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetImageList @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {
    operator fun invoke(): Flow<Result<List<CatImage>>> = flow {
        withContext(Dispatchers.IO) {
            emit(Result.Success(localRepository.readImageList()))
            remoteRepository.getAllImages().collect { allImageRes ->
                when (allImageRes) {
                    is Result.Error -> emit(Result.Error(allImageRes.exception))
                    is Result.Loading -> emit(Result.Loading())
                    is Result.Success -> {
                        allImageRes.data.forEach {
                            localRepository.insertImage(it)
                        }
                        emit(Result.Success(allImageRes.data))
                    }
                }
            }
        }
    }
}