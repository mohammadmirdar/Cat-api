package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.remote.common.Result
import com.mirdar.catapiapp.domain.model.CatImage
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
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SetImageFavouriteTest {

    private val localRepository: LocalRepository = mock()
    private lateinit var setImageFavourite: SetImageFavourite
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        setImageFavourite = SetImageFavourite(localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should update favourite status and emit updated image`() = runTest {
        val imageId = "1"
        val isFavourite = true
        val updatedCatImage = CatImage(id = "1", url = "url1", isFavourite = isFavourite)

        whenever(localRepository.setImageFavourite(imageId, isFavourite)).then {}
        whenever(localRepository.readImageList()).thenReturn(
            listOf(updatedCatImage, CatImage(id = "2", url = "url2", isFavourite = false))
        )

        val flow = setImageFavourite(imageId, isFavourite)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(updatedCatImage, (result[0] as Result.Success).data)

        verify(localRepository).setImageFavourite(imageId, isFavourite)
        verify(localRepository).readImageList()
    }

    @Test
    fun `invoke should throw exception if imageId is not found`() = runTest {
        val imageId = "3"
        val isFavourite = true

        whenever(localRepository.setImageFavourite(any(), any())).then {}
        whenever(localRepository.readImageList()).thenReturn(
            listOf(CatImage(id = "1", url = "url1", isFavourite = false))
        )

        val flow = setImageFavourite(imageId, isFavourite)
        val result = runCatching { flow.toList() }

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoSuchElementException)

        verify(localRepository).setImageFavourite(imageId, isFavourite)
        verify(localRepository).readImageList()
    }
}
