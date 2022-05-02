package com.rhymartmanchus.mockroutiq.data

import com.rhymartmanchus.mockroutiq.domain.RouteDetail

interface RoutesRemoteService {

    suspend fun fetchRouteDetails(): RouteDetail

}