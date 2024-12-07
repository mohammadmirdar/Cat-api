package com.mirdar.catapiapp.di

import com.mirdar.catapiapp.data.remote.CatApiService
import com.mirdar.catapiapp.data.remote.RemoteRepository
import com.mirdar.catapiapp.data.remote.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRemoteRepository(catApiService: CatApiService): RemoteRepository =
        RemoteRepositoryImpl(catApiService)
}