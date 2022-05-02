package com.rhymartmanchus.mockroutiq.domain

import javax.inject.Inject

class FetchRouteDetailsUseCase @Inject constructor(
    private val gateway: RoutesGateway
) : UseCase<Unit, FetchRouteDetailsUseCase.Response>() {

    data class Response(
        val routeDetail: RouteDetail
    )

    override suspend fun execute(params: Unit): Response =
        Response(
            gateway.fetchRouteDetail()
        )

}