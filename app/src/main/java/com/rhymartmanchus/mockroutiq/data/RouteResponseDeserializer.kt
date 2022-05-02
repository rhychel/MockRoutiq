package com.rhymartmanchus.mockroutiq.data

import com.google.gson.*
import com.rhymartmanchus.mockroutiq.domain.PlaceOfInterest
import com.rhymartmanchus.mockroutiq.domain.RouteDetail
import java.lang.reflect.Type

class RouteResponseDeserializer : JsonDeserializer<RouteDetail> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RouteDetail {
        val data = json.asJsonObject.getAsJsonObject("data")
        val dataAttributes = getDataAttributes(data)
        val included = json.asJsonObject.getAsJsonArray("included")

        val startPoint = getStartPoint(
            getRelationshipStartPoint(data)
                .getAsJsonObject("data")
                .get("id").asString,
            included
        )

        val images = getRelationshipImages(data)
            .getAsJsonArray("data")
            .map {
                getImage(
                    it.asJsonObject.get("id").asString,
                    included
                )?.getAsJsonObject("attributes")
                    ?.get("URL")?.asString
            }.requireNoNulls()

        val placeOfInterests = getPlaceOfInterests(data, included)

        return RouteDetail(
            dataAttributes.get("name").asString,
            startPoint?.getAsJsonObject("attributes")?.get("name")?.asString ?: "N/A",
            dataAttributes.get("description").asString,
            dataAttributes.get("shortDescription").asString,
            dataAttributes.get("routeMapImgUrl").asString,
            dataAttributes.get("ratingCount").asInt,
            dataAttributes.get("averageRating").asInt,
            dataAttributes.get("totalDistance").asInt,
            dataAttributes.get("totalTime").asInt,
            dataAttributes.get("elevationMeters").asInt,
            images,
            placeOfInterests
        )
    }

    private fun getDataAttributes(data: JsonObject): JsonObject =
        data.getAsJsonObject("attributes")

    private fun getRelationshipImages(data: JsonObject): JsonObject =
        data.getAsJsonObject("relationships")
            .getAsJsonObject("images")

    private fun getRelationshipStartPoint(data: JsonObject): JsonObject =
        data.getAsJsonObject("relationships")
            .getAsJsonObject("startPoint")

    private fun getRelationshipCategoriesData(data: JsonObject): JsonArray =
        data.getAsJsonObject("relationships")
            .getAsJsonObject("categories")
            .getAsJsonArray("data")

    private fun getCategory(id: String, included: JsonArray): String =
        included
            .filter {
                it.asJsonObject.get("type").asString == "categories"
            }
            .find {
                it.asJsonObject.get("id").asString == id
            }?.asJsonObject?.getAsJsonObject("attributes")
            ?.get("name")?.asString ?: "Unfound"

    private fun findPointOfInterest(categoryId: String, included: JsonArray): JsonObject? =
        included
            .filter {
                it.asJsonObject.get("type").asString == "pois"
            }
            .find {
                it.asJsonObject
                    .getAsJsonObject("relationships")
                    .getAsJsonObject("category")
                    .getAsJsonObject("data")
                    .get("id").asString == categoryId
            }?.asJsonObject

    private fun getStartPoint(id: String, included: JsonArray): JsonObject? =
        included
            .filter {
                it.asJsonObject.get("type").asString == "routepoints"
            }
            .find {
                it.asJsonObject.get("id").asString == id
            }?.asJsonObject

    private fun getImage(id: String, included: JsonArray): JsonObject? =
        included
            .filter {
                it.asJsonObject.get("type").asString == "images"
            }
            .find {
                it.asJsonObject.get("id").asString == id
            }?.asJsonObject

    private fun getPlaceOfInterests(data: JsonObject, included: JsonArray): List<PlaceOfInterest> {
        val placeOfInterests = mutableListOf<PlaceOfInterest>()
        getRelationshipCategoriesData(data)
            .forEach {
                val categoryId = it.asJsonObject.get("id").asString
                val category = getCategory(categoryId, included)
                findPointOfInterest(categoryId, included)?.let { poi ->
                    val attribute = poi.getAsJsonObject("attributes")
                    val imageUrl = getImage(
                        poi.getAsJsonObject("relationships")
                            .getAsJsonObject("mainPicture")
                            .getAsJsonObject("data")
                            .get("id").asString,
                        included
                    )?.getAsJsonObject("attributes")
                        ?.get("URL")?.asString ?: ""
                    placeOfInterests.add(
                        PlaceOfInterest(
                            attribute.get("name").asString,
                            category,
                            attribute.get("description").asString,
                            imageUrl
                        )
                    )
                }
            }

        return placeOfInterests
    }

}