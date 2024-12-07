package com.mirdar.catapiapp.data.remote

import com.mirdar.catapiapp.domain.model.CatImage
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.vapebothq.vendingmachineapp.data.common.Result
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getAllImages(): Flow<Result<List<CatImage>>>
    fun getImageDetail(imageId: String): Flow<Result<ImageDetail>>
}