package com.mirdar.catapiapp.data.local

import com.mirdar.catapiapp.data.local.model.RealmImage
import com.mirdar.catapiapp.data.local.model.RealmImageDetail
import com.mirdar.catapiapp.data.local.model.toDomain
import com.mirdar.catapiapp.domain.model.CatImage
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.mirdar.catapiapp.domain.model.toRealm
import io.realm.kotlin.Realm
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val realm: Realm
) : LocalRepository {
    override suspend fun insertImage(image: CatImage) {
        realm.writeBlocking {
            this.copyToRealm(image.toRealm())
        }
    }

    override suspend fun insertImageDetail(imageDetail: ImageDetail) {
        realm.writeBlocking {
            this.copyToRealm(imageDetail.toRealm())
        }
    }

    override suspend fun readImageList(): List<CatImage> {
        return realm.where(RealmImage::class).findAll().map { it.toDomain() }
    }

    override suspend fun readImageDetail(imageId: String): ImageDetail? {
        return realm.where(RealmImageDetail::class).equalTo("id", imageId).findFirst()?.toDomain()
    }

    override suspend fun clearImageList() {
        realm.writeBlocking {
            this.delete(RealmImage::class)
        }
    }
}