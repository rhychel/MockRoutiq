package com.rhymartmanchus.mockroutiq.data

import com.rhymartmanchus.mockroutiq.domain.RouteDetail
import com.rhymartmanchus.mockroutiq.domain.RoutesGateway
import javax.inject.Inject

class RoutesRepository @Inject constructor(
    private val remote: RoutesRemoteService
): RoutesGateway {

    override suspend fun fetchRouteDetail(): RouteDetail =
        remote.fetchRouteDetails()

}