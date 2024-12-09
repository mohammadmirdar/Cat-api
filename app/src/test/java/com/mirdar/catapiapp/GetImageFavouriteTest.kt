package com.mirdar.catapiapp

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.domain.GetImageFavourite
import com.mirdar.catapiapp.domain.model.ImageDetail
import com.mirdar.catapiapp.data.remote.common.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetImageFavouriteTest {

    private val localRepository: LocalRepository = mock()
    private lateinit var getImageFavourite: GetImageFavourite
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getImageFavourite = GetImageFavourite(localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should update favourite status and emit updated data`() = runTest {
        val imageId = "test_image"
        val isFavourite = true
        val updatedImageDetail = ImageDetail(
            id = "test_image",
            url = "https://example.com/cat.jpg",
            isFavourite = isFavourite
        )

        whenever(localRepository.setImageFavourite(imageId, isFavourite)).then {}
        whenever(localRepository.readImageDetail(imageId)).thenReturn(updatedImageDetail)

        val flow = getImageFavourite(imageId, isFavourite)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertEquals(updatedImageDetail, result[0].data)

        verify(localRepository).setImageFavourite(imageId, isFavourite)
        verify(localRepository).readImageDetail(imageId)
    }

    @Test
    fun `invoke should emit error if setImageFavourite fails`() = runTest {
        val imageId = "test_image"
        val isFavourite = true
        val exception = RuntimeException("Failed to update favourite status")

        whenever(localRepository.setImageFavourite(imageId, isFavourite)).thenThrow(exception)

        val flow = getImageFavourite(imageId, isFavourite)
        val result = runCatching { flow.toList() }

        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)

        verify(localRepository).setImageFavourite(imageId, isFavourite)
        verify(localRepository, never()).readImageDetail(imageId)
    }
}
