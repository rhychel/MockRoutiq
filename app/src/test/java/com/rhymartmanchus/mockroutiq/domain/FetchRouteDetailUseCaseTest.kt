package com.rhymartmanchus.mockroutiq.domain

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchRouteDetailUseCaseTest {

    @MockK
    private lateinit var gateway: RoutesGateway
    private lateinit var useCase: FetchRouteDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        useCase = FetchRouteDetailsUseCase(gateway)

    }

    private fun stubFetchRouteDetail(): RouteDetail {
        val mockRouteDetail = mockk<RouteDetail>()
        coEvery {
            gateway.fetchRouteDetail()
        } returns mockRouteDetail
        return mockRouteDetail
    }

    @Test
    fun `should be able to get route details`() = runBlocking {
        val mockRouteDetail = stubFetchRouteDetail()

        val result = useCase.execute(Unit)

        assertEquals(mockRouteDetail, result.routeDetail)
    }

    @Test
    fun `should be able to get places of interest`() = runBlocking {
        val placesOfInterest = listOf<PlaceOfInterest>(
            mockk(),
            mockk(),
            mockk())

        val mockRouteDetail = mockk<RouteDetail>(relaxed = true)

        every {
            mockRouteDetail.placeOfInterests
        } returns placesOfInterest

        coEvery {
            gateway.fetchRouteDetail()
        } returns mockRouteDetail

        val result = useCase.execute(Unit)

        assertEquals(placesOfInterest, result.routeDetail.placeOfInterests)
    }

    @Test
    fun `should be able to get images of route details`() = runBlocking {
        val images = listOf("A", "B", "C")

        val mockRouteDetail = mockk<RouteDetail>(relaxed = true)

        every {
            mockRouteDetail.images
        } returns images

        coEvery {
            gateway.fetchRouteDetail()
        } returns mockRouteDetail

        val result = useCase.execute(Unit)

        assertEquals(images, result.routeDetail.images)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}