package com.rhymartmanchus.mockroutiq.domain

import kotlin.coroutines.CoroutineContext

interface IAppDispatchers {

    fun io(): CoroutineContext
    fun ui(): CoroutineContext

}