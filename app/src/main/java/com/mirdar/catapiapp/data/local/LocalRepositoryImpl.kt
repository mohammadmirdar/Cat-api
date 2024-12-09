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
            val isFavourite = realm.where(RealmImage::class).equalTo("id", imageDetail.id).findFirst()?.isFavourite
            this.copyToRealm(imageDetail.toRealm().apply { this.isFavourite = isFavourite ?: false })
        }
    }

    override suspend fun readImageList(): List<CatImage> {
        return realm.where(RealmImage::class).findAll().map { it.toDomain() }
    }

    override suspend fun readImageDetail(imageId: String): ImageDetail? {
        return realm.where(RealmImageDetail::class).equalTo("id", imageId).findFirst()?.toDomain()
    }

    override suspend fun setImageFavourite(imageId: String, isFavourite: Boolean) {
        realm.writeBlocking {
            this.where(RealmImageDetail::class).equalTo("id", imageId).findFirst()?.isFavourite =
                isFavourite
            this.where(RealmImage::class).equalTo("id", imageId).findFirst()?.isFavourite =
                isFavourite
        }
    }

    override suspend fun clearImageList() {
        realm.writeBlocking {
            this.delete(RealmImage::class)
        }
    }
}