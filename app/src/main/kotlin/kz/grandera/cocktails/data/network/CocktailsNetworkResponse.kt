package kz.grandera.cocktails.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailsNetworkResponse(
    @SerialName("drinks")
    val cocktails: List<CocktailNetwork>,
)
