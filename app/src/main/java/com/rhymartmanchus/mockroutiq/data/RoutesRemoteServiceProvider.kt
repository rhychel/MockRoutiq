package com.rhymartmanchus.mockroutiq.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rhymartmanchus.mockroutiq.domain.RouteDetail
import javax.inject.Inject

class RoutesRemoteServiceProvider @Inject constructor(
    private val assetManager: AssetManager
) : RoutesRemoteService {

    lateinit var gson: Gson

    override suspend fun fetchRouteDetails(): RouteDetail {
        gson = GsonBuilder()
            .registerTypeAdapter(RouteDetail::class.java, RouteResponseDeserializer())
            .create()

        val routeResponse = assetManager.open("route-response.json")
            .bufferedReader().use {
                it.readText()
            }

        return gson.fromJson(routeResponse, RouteDetail::class.java)
    }

}