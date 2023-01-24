package com.example.utils.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Вот это уже наверное перебор)
abstract class BaseCoroutineUseCase<ParamsType, ReturnType> {

    open val dispatcher: CoroutineDispatcher = Dispatchers.IO

    abstract suspend fun buildUseCase(params: ParamsType): ReturnType

    suspend operator fun invoke(params: ParamsType) = withContext(dispatcher) {
        buildUseCase(params)
    }
}