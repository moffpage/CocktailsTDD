package kz.grandera.cocktails.domain.cocktail

data class Cocktail(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val isAlcoholic: Boolean,
)
