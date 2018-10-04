package main

import com.github.kittinunf.fuel.Fuel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import songsModels.Songs
//import dbsongsmodels.Songs

fun main(args: Array<String>) {
    val url = "https://next.json-generator.com/api/json/get/EkeSgmXycS"
    val (request, response, result) = Fuel.get(url).responseObject(songsModels.Songs.SongsArrayDeserializer())
    val (songs, err) = result
    songs?.forEach {
        println(it)
    }
    Database.connect(
            "jdbc:postgresql:misctests",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )
    transaction {
        SchemaUtils.create(main.dbsongsmodels.Songs)
        songs?.forEach {
                main.dbsongsmodels.Songs.insert {
                    id = integer("id").autoIncrement().primaryKey()
                    year = varchar("year", length = 50)
                    country = varchar("country", length = 50)
                    region= varchar("region", length = 50)
                    artistName = varchar("artistName", length = 50)
                    song = varchar("song", length = 50)
                    artistGender = varchar("artistName", length = 50)
                    groupOrSolo = varchar("groupOrSolo", length = 50)
                    place = varchar("place", length = 50)
                    points = varchar("points", length = 50)
                    isFinal = varchar("isFinal", length = 50)
                    isSongInEnglish = varchar("isSongInEnglish", length = 50)
                    normalizedPoints = varchar("normalizePoints", length = 50)
                    energy = varchar("energy", length = 50)
                    duration = varchar("duration", length = 50)
                    acusticness = varchar("acustiness", length = 50)
                    danceability = varchar("danceability", length = 50)
                    tempo = varchar("tempo", length = 50)
                    speechiness = varchar("speachiness", length = 50)
                    key = varchar("key", length = 50)
                    liveness = varchar("liveness", length = 50)
                    timeSignature = varchar("timeSignature", length = 50)
                    mode = varchar("mode", length = 50)
                    loudness = varchar("loudness", length = 50)
                    valence = varchar("valance", length = 50)
                    happiness = varchar("happiness", length = 50)
                }
            }
    }
}

