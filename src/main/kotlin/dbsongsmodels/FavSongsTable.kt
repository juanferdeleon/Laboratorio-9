package main.dbsongsmodels

import org.jetbrains.exposed.sql.Table

object favSongs: Table(){
    val id = integer("id").autoIncrement().primaryKey()
    val year = varchar("year", length = 50)
    val country = varchar("country", length = 50)
    val region= varchar("region", length = 50)
    val artistName = varchar("artistName", length = 50)
    val song = varchar("song", length = 50)
    val artistGender = varchar("artistName", length = 50)
    val groupOrSolo = varchar("groupOrSolo", length = 50)
    val place = varchar("place", length = 50)
    val points = varchar("points", length = 50)
    val isFinal = varchar("isFinal", length = 50)
    val isSongInEnglish = varchar("isSongInEnglish", length = 50)
    val normalizedPoints = varchar("normalizePoints", length = 50)
    val energy = varchar("energy", length = 50)
    val duration = varchar("duration", length = 50)
    val acusticness = varchar("acustiness", length = 50)
    val danceability = varchar("danceability", length = 50)
    val tempo = varchar("tempo", length = 50)
    val speechiness = varchar("speachiness", length = 50)
    val key = varchar("key", length = 50)
    val liveness = varchar("liveness", length = 50)
    val timeSignature = varchar("timeSignature", length = 50)
    val mode = varchar("mode", length = 50)
    val loudness = varchar("loudness", length = 50)
    val valence = varchar("valance", length = 50)
    val happiness = varchar("happiness", length = 50)


}