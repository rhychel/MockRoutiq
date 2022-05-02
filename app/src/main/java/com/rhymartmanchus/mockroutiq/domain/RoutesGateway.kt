package com.rhymartmanchus.mockroutiq.domain

interface RoutesGateway {

    suspend fun fetchRouteDetail(): RouteDetail

}