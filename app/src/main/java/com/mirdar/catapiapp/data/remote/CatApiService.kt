package com.mirdar.catapiapp.data.remote

import com.mirdar.catapiapp.data.remote.model.ImageDetailRes
import com.mirdar.catapiapp.data.remote.model.ImageRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApiService {

    @GET("search")
    suspend fun getImages(
        @Query("limit") limit: Int = 40
    ) : Response<List<ImageRes>>


    @GET(".")
    suspend fun getImageDetail(
        @Path("id") imageId : String
    ) : Response<ImageDetailRes>
}