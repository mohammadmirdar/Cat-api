import com.mirdar.catapiapp.data.local.model.RealmBreed
import com.mirdar.catapiapp.data.local.model.RealmImage
import com.mirdar.catapiapp.data.local.model.RealmImageDetail
import com.mirdar.catapiapp.data.local.model.RealmWeight
import com.mirdar.catapiapp.data.remote.CatApiService
import com.mirdar.catapiapp.data.remote.common.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(): RequestInterceptor = RequestInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideYotiRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(AppUtils.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCatApiService(retrofit: Retrofit) : CatApiService = retrofit.create(CatApiService::class.java)


    @Provides
    @Singleton
    fun provideRealmDatabase(): Realm {
        val config = RealmConfiguration.create(
            schema = setOf(
                RealmImage::class,
                RealmImageDetail::class,
                RealmBreed::class,
                RealmWeight::class
            )
        )
        return Realm.open(config)
    }
}