package com.mirdar.catapiapp.data.local

import com.mirdar.catapiapp.domain.model.CatImage
import com.mirdar.catapiapp.domain.model.ImageDetail
import io.realm.kotlin.Realm
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val realm: Realm
) : LocalRepository{
    override suspend fun insertImage(image: CatImage) {
        realm
    }

    override suspend fun insertImageDetail(imageDetail: ImageDetail) {
        TODO("Not yet implemented")
    }

    override suspend fun readImageList(): List<CatImage> {
        TODO("Not yet implemented")
    }

    override suspend fun readImageDetail(imageId: String): ImageDetail {
        TODO("Not yet implemented")
    }
}