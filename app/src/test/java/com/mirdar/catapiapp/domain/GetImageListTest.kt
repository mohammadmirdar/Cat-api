package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.remote.RemoteRepository
import com.mirdar.catapiapp.data.remote.common.CallErrors
import com.mirdar.catapiapp.data.remote.common.Result
import com.mirdar.catapiapp.domain.model.CatImage
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetImageListTest {

    private val localRepository: LocalRepository = mock()
    private val remoteRepository: RemoteRepository = mock()
    private lateinit var getImageList: GetImageList
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getImageList = GetImageList(remoteRepository, localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should return image list from local storage if not empty`() = runTest {
        val imageList = listOf(
            CatImage(id = "1", url = "url1", isFavourite = false),
            CatImage(id = "2", url = "url2", isFavourite = true)
        )

        whenever(localRepository.readImageList()).thenReturn(imageList)

        val flow = getImageList()
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(imageList, (result[0] as Result.Success).data)

        verify(localRepository).readImageList()
        verify(remoteRepository, times(0)).getAllImages()
    }

    @Test
    fun `invoke should fetch images from remote if local storage is empty`() = runTest {
        val remoteImageList = listOf(
            CatImage(id = "3", url = "url3", isFavourite = false),
            CatImage(id = "4", url = "url4", isFavourite = true)
        )

        whenever(localRepository.readImageList()).thenReturn(emptyList())
        whenever(remoteRepository.getAllImages()).thenReturn(flow {
            emit(Result.Success(remoteImageList))
        })

        val flow = getImageList()
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(remoteImageList, (result[0] as Result.Success).data)

        verify(localRepository).readImageList()
        verify(remoteRepository).getAllImages()
        verify(localRepository).clearImageList()
        verify(localRepository, times(remoteImageList.size)).insertImage(any())
    }

    @Test
    fun `invoke should emit error if remote fetch fails`() = runTest {
        val exception = CallErrors.ErrorServer

        whenever(localRepository.readImageList()).thenReturn(emptyList())
        whenever(remoteRepository.getAllImages()).thenReturn(flow {
            emit(Result.Error(CallErrors.ErrorServer))
        })

        val flow = getImageList()
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Error)
        assertEquals(exception, (result[0] as Result.Error).exception)

        verify(localRepository).readImageList()
        verify(remoteRepository).getAllImages()
        verify(localRepository, times(0)).clearImageList()
    }
}
