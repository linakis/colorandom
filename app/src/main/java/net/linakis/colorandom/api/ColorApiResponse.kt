package net.linakis.colorandom.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ColorApiResponse(@SerialName("name") val name: ColorName)

@Serializable
data class ColorName(@SerialName("value") val colorName: String)