package songsModels

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Songs(val year: String, val country: String, val region: String, val artistName: String, val song: String, val artistGender: String, val groupOrSolo: String, val place: String, val points: String, val isFinal: String, val isSongInEnglish: String, val normalizedPoints: String, val energy: String, val duration: String, val acusticness: String, val danceability: String, val tempo: String, val speechiness: String, val key: String, val liveness: String, val timeSignature: String, val mode: String, val loudness: String, val valence: String, val happiness: String) {
   class SongsArrayDeserializer: ResponseDeserializable<Array<Songs>> {
       override fun deserialize(content: String): Array<Songs>? {
           return Gson().fromJson(content, Array<Songs>::class.java)
       }
   }

    override fun toString(): String {
        return """
            Year: $year
            Country: $country
            Region: $region
            Artist Name: $artistName
            Song: $song
            Artist Gender: $artistGender
            groupOrSolo: $groupOrSolo
            place: $place
            points: $points
            isFinal: $isFinal
            isSongInEnglish: $isSongInEnglish
            normalizedPoints: $normalizedPoints
            energy: $energy
            duration: $duration
            acusticness: $acusticness
            danceability: $danceability
            tempo: $tempo
            speechiness: $speechiness
            key: $key
            liveness: $liveness
            timeSignature: $timeSignature
            mode: $mode
            loudness: $loudness
            valence: $valence
            happiness: $happiness
        """.trimIndent()
    }
}