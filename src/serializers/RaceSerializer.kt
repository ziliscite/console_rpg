package serializers

import com.google.gson.annotations.SerializedName

data class RaceName(val name: String, val url: String)
data class Races(@SerializedName("results") val races: Array<RaceName>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Races

        return races.contentEquals(other.races)
    }

    override fun hashCode(): Int {
        return races.contentHashCode()
    }
}

data class Race(
    val name: String, val url: String, val speed: Int,
    val age: String, val alignment: String,
    @SerializedName("ability_bonuses") val abilityBonuses: Array<AbilityBonus>,
    val traits: Array<Trait>,
    @SerializedName("subraces") val subRaces: Array<SubRace>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Race

        if (name != other.name) return false
        if (speed != other.speed) return false
        if (!abilityBonuses.contentEquals(other.abilityBonuses)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + speed
        result = 31 * result + abilityBonuses.contentHashCode()
        return result
    }
}

data class AbilityScore(val name: String, val url: String)
data class AbilityBonus(@SerializedName("ability_score") val abilityScore: AbilityScore, val bonus: Int)

data class Trait(val name: String, val url: String)

data class SubRace(val name: String, val url: String)