package com.mirdar.catapiapp.domain

import com.mirdar.catapiapp.data.local.LocalRepository
import com.mirdar.catapiapp.data.remote.RemoteRepository
import com.mirdar.catapiapp.data.remote.common.CallErrors
import com.mirdar.catapiapp.domain.model.ImageDetail
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.kotlin.whenever
import com.mirdar.catapiapp.data.remote.common.Result
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.any
import org.mockito.kotlin.times

@ExperimentalCoroutinesApi
class GetImageDetailTest {

    private val localRepository: LocalRepository = mock()
    private val remoteRepository: RemoteRepository = mock()

    private lateinit var getImageDetail: GetImageDetail

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getImageDetail = GetImageDetail(localRepository, remoteRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should emit local data if available`() = runTest {
        val imageId = "test_image"
        val localImageDetail = ImageDetail("test_image", 0, 0, "test")
        whenever(localRepository.readImageDetail(imageId)).thenReturn(localImageDetail)

        val flow = getImageDetail(imageId)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(localImageDetail, (result[0] as Result.Success).data)

        verify(localRepository).readImageDetail(imageId)
        verifyNoInteractions(remoteRepository)
    }

    @Test
    fun `invoke should fetch data from remote if local data is null and emit success`() = runTest {
        val imageId = "test_image"
        val remoteImageDetail = ImageDetail("test_image", 1204, 1445, "url",isFavourite = false, emptyList())
        val finalImageDetail = ImageDetail("test_image", 1204, 1445, "url",isFavourite = false, emptyList())
        
        whenever(localRepository.readImageDetail(imageId)).thenReturn(null)
        whenever(remoteRepository.getImageDetail(imageId)).thenReturn(flowOf(Result.Success(remoteImageDetail)))
        whenever(localRepository.insertImageDetail(any())).then {
            kotlinx.coroutines.test.runTest {
                whenever(localRepository.readImageDetail(imageId)).thenReturn(finalImageDetail)
            }
        }

        val flow = getImageDetail(imageId)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Success)
        assertEquals(finalImageDetail, (result[0] as Result.Success).data)


        verify(localRepository, times(2)).readImageDetail(imageId)
        verify(remoteRepository).getImageDetail(imageId)
        verify(localRepository).insertImageDetail(remoteImageDetail)
    }

    @Test
    fun `invoke should emit error if remote fetch fails`() = runTest {
        val imageId = "test_image"
        val error = Result.Error(CallErrors.ErrorServer)

        whenever(localRepository.readImageDetail(imageId)).thenReturn(null)
        whenever(remoteRepository.getImageDetail(imageId)).thenReturn(flowOf(error))

        val flow = getImageDetail(imageId)
        val result = flow.toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Result.Error)

        verify(localRepository).readImageDetail(imageId)
        verify(remoteRepository).getImageDetail(imageId)
        verifyNoMoreInteractions(localRepository)
    }
}
