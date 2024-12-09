package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.remote.common.Result
import com.mirdar.catapiapp.domain.model.ImageDetail
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SetImageDetailFavouriteTest {

    private val localRepository: LocalRepository = mock()
    private lateinit var setImageDetailFavourite: SetImageDetailFavourite
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        setImageDetailFavourite = SetImageDetailFavourite(localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should update favourite status and emit updated image detail`() = runTest {
        val imageId = "1"
        val isFavourite = true
        val updatedImageDetail = ImageDetail(
            id = imageId,
            url = "url1",
            width = 100,
            height = 100,
            isFavourite = isFavourite
        )

        whenever(localRepository.setImageFavourite(imageId, isFavourite)).then {}
        whenever(localRepository.readImageDetail(imageId)).thenReturn(updatedImageDetail)

        val flow = setImageDetailFavourite(imageId, isFavourite)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(updatedImageDetail, (result[0] as Result.Success).data)

        verify(localRepository).setImageFavourite(imageId, isFavourite)
        verify(localRepository).readImageDetail(imageId)
    }

    @Test
    fun `invoke should return null if image detail does not exist`() = runTest {
        val imageId = "2"
        val isFavourite = false

        whenever(localRepository.setImageFavourite(imageId, isFavourite)).then {}
        whenever(localRepository.readImageDetail(imageId)).thenReturn(null)

        val flow = setImageDetailFavourite(imageId, isFavourite)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(null, (result[0] as Result.Success).data)

        verify(localRepository).setImageFavourite(imageId, isFavourite)
        verify(localRepository).readImageDetail(imageId)
    }
}
