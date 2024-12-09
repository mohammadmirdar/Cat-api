package com.mirdar.catapiapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mirdar.catapiapp.data.remote.CatApiService
import com.mirdar.catapiapp.data.remote.RemoteRepositoryImpl
import com.mirdar.catapiapp.data.remote.common.CallErrors
import com.mirdar.catapiapp.data.remote.common.Result
import com.mirdar.catapiapp.domain.model.CatImage
import com.mirdar.catapiapp.domain.model.ImageDetail
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class RemoteRepositoryIntegrationTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var catApiService: CatApiService
    private lateinit var remoteRepository: RemoteRepositoryImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        catApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApiService::class.java)

        remoteRepository = RemoteRepositoryImpl(catApiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAllImages should return a list of CatImage when API call is successful`() = runTest {
        val mockResponse = """
            [
                {
                    "id": "1",
                    "url": "https://cdn2.thecatapi.com/images/1.jpg",
                    "width": 800,
                    "height": 600
                }
            ]
        """
        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val flow = remoteRepository.getAllImages()
        val result = flow.toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Success)
        val images = (result[1] as Result.Success).data
        assertEquals(1, images.size)
        assertEquals(CatImage("1", "https://cdn2.thecatapi.com/images/1.jpg", 800, 600, false), images[0])
    }

    @Test
    fun `getAllImages should return error when API call fails`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val flow = remoteRepository.getAllImages()
        val result = flow.toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
    }

    @Test
    fun `getImageDetail should return ImageDetail when API call is successful`() = runTest {
        val mockResponse = """
            {
                "id": "1",
                "url": "https://cdn2.thecatapi.com/images/1.jpg",
                "width": 800,
                "height": 600,
                "breeds": []
            }
        """
        mockWebServer.enqueue(MockResponse().setBody(mockResponse).setResponseCode(200))

        val flow = remoteRepository.getImageDetail("1")
        val result = flow.toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Success)
        val imageDetail = (result[1] as Result.Success).data
        assertEquals(ImageDetail("1", 800, 600, "https://cdn2.thecatapi.com/images/1.jpg", false), imageDetail)
    }

    @Test
    fun `getImageDetail should return error when API call fails`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val flow = remoteRepository.getImageDetail("1")
        val result = flow.toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Result.Loading)
        assertTrue(result[1] is Result.Error)
    }
}
