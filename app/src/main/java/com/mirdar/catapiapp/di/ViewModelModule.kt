package com.mirdar.catapiapp.di

import androidx.compose.ui.graphics.vector.VectorComposable
import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.local.LocalRepositoryImpl
import com.mirdar.catapiapp.data.remote.CatApiService
import com.mirdar.catapiapp.data.remote.RemoteRepository
import com.mirdar.catapiapp.data.remote.RemoteRepositoryImpl
import com.mirdar.catapiapp.domain.GetImageDetail
import com.mirdar.catapiapp.domain.GetImageList
import com.mirdar.catapiapp.domain.SetImageDetailFavourite
import com.mirdar.catapiapp.domain.SetImageFavourite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.realm.kotlin.Realm

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRemoteRepository(catApiService: CatApiService): RemoteRepository =
        RemoteRepositoryImpl(catApiService)

    @Provides
    @ViewModelScoped
    fun provideLocalRepository(realm: Realm): LocalRepository = LocalRepositoryImpl(realm)

    @Provides
    @ViewModelScoped
    fun provideGetImageList(remoteRepository: RemoteRepository, localRepository: LocalRepository) =
        GetImageList(remoteRepository, localRepository)

    @Provides
    @ViewModelScoped
    fun provideGetImageDetail(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ) = GetImageDetail(localRepository, remoteRepository)

    @Provides
    @ViewModelScoped
    fun provideSetImageDetailFavourite(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ) = SetImageDetailFavourite(localRepository)

    @Provides
    @ViewModelScoped
    fun provideSetImageFavourite(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ) = SetImageFavourite(localRepository)

}