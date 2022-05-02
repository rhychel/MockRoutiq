package com.rhymartmanchus.mockroutiq.domain

abstract class UseCase <Params, Response> {

    abstract suspend fun execute(params: Params): Response

}