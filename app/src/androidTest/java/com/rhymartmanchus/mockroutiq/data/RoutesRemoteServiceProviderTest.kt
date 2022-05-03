package com.rhymartmanchus.mockroutiq.data

import android.content.res.AssetManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rhymartmanchus.mockroutiq.domain.RouteDetail
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class RoutesRemoteServiceProviderTest {

    lateinit var routesRemoteService: RoutesRemoteService

    private lateinit var assets: AssetManager

    @Before
    fun setUp() {
        assets = InstrumentationRegistry.getInstrumentation().context.assets

        routesRemoteService = RoutesRemoteServiceProvider(assets)

    }

    @Test
    fun test_fetchRouteDetails_routeName() = runBlocking {
        val routeDetail = routesRemoteService.fetchRouteDetails()

        assertEquals("Vecht valley route: follow the Vecht to Germany", routeDetail.name)
    }

    @Test
    fun test_fetchRouteDetails_startLocation() = runBlocking {
        val routeDetail = routesRemoteService.fetchRouteDetails()

        assertEquals("48720, Rosendahl, North Rhine-Westphalia, Germany", routeDetail.startLocation)
    }

    @Test
    fun test_fetchRouteDetails_images() = runBlocking {
        val routeDetail = routesRemoteService.fetchRouteDetails()

        assertEquals(3, routeDetail.images.count())
    }

    @Test
    fun test_fetchRouteDetails_placeOfInterests() = runBlocking {
        val routeDetail = routesRemoteService.fetchRouteDetails()

        assert(routeDetail.placeOfInterests.isNotEmpty())
    }

    fun test_fetchRouteDetails_routeProvider() = runBlocking {
        val routeDetail = routesRemoteService.fetchRouteDetails()

        assertEquals("route.nl", routeDetail.providedBy!!.name)
    }

    @Test
    fun test_millis() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = 49694.18823529412.toLong()
        println(SimpleDateFormat("mm:ss").format(calendar.time))

    }

}