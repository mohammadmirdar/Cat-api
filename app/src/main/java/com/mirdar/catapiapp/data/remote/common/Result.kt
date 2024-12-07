package com.vapebothq.vendingmachineapp.data.common

import com.mirdar.catapiapp.data.remote.common.CallErrors

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: CallErrors) : Result<Nothing>()
    data class Loading(val progress: Int = 0) : Result<Nothing>()
}