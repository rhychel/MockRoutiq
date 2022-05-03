package com.rhymartmanchus.mockroutiq.domain

data class RouteDetail (
    val name: String,
    val startLocation: String,
    val description: String,
    val shortDescription: String,
    val mapImageUrl: String,
    val ratingCount: Int,
    val averageRating: Int,
    val distance: Int,
    val millisTime: Int,
    val elevationMeters: Int,
    val images: List<String>,
    val placeOfInterests: List<PlaceOfInterest>,
    val providedBy: RouteProvider?
)