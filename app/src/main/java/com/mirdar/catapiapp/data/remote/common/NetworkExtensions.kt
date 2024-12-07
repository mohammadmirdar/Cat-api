package com.mirdar.catapiapp.data.remote.common

import AppUtils
import com.vapebothq.vendingmachineapp.data.common.CallErrors
import com.vapebothq.vendingmachineapp.data.common.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

fun <T : Any> Flow<Result<T>>.applyCommonSideEffects() =
    retryWhen { cause, attempt ->
        when {
            (cause is IOException && attempt < AppUtils.MAX_RETRIES) -> {
                delay(AppUtils.getBackoffDelay(attempt))
                true
            }

            else -> {
                false
            }
        }
    }.onStart {
        emit(Result.Loading())
    }.catch {
        emit(Result.Error(CallErrors.ErrorException(it)))
    }