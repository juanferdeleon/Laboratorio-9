package main

import com.github.kittinunf.fuel.Fuel
import main.dbsongsmodels.Songs
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>) {
    readAndSetData()

}

fun readAndSetData(){
    //Lee la informacion desde la API
    val url = "https://next.json-generator.com/api/json/get/EkeSgmXycS"
    val (request, response, result) = Fuel.get(url).responseObject(songsModels.Songs.SongsArrayDeserializer())
    val (songs, err) = result

    //Conceta con la db local
    Database.connect(
            "jdbc:postgresql:songs",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )

    //Agrega los registros a la db y crea las tablas si no existen
    transaction {
        SchemaUtils.create(Songs)

        var x = 0
        while (x < songs!!.size){
            Songs.insert {
                it[year] = "${songs[x].year}"
                it[country] = "${songs[x].country}"
                it[region] = "${songs[x].region}"
                it[artistName] = "${songs[x].artistName}"
                it[song] = "${songs[x].song}"
                it[artistGender] = "${songs[x].artistGender}"
                it[groupOrSolo] = "${songs[x].groupOrSolo}"
                it[place] = "${songs[x].place}"
                it[points] = "${songs[x].points}"
                it[isFinal] = "${songs[x].isFinal}"
                it[isSongInEnglish] = "${songs[x].isSongInEnglish}"
                it[normalizedPoints] = "${songs[x].normalizedPoints}"
                it[energy] = "${songs[x].energy}"
                it[duration] = "${songs[x].duration}"
                it[acusticness] = "${songs[x].acusticness}"
                it[danceability] = "${songs[x].danceability}"
                it[tempo] = "${songs[x].tempo}"
                it[speechiness] = "${songs[x].speechiness}"
                it[key] = "${songs[x].key}"
                it[liveness] = "${songs[x].liveness}"
                it[timeSignature] = "${songs[x].timeSignature}"
                it[mode] = "${songs[x].mode}"
                it[loudness] = "${songs[x].loudness}"
                it[valence] = "${songs[x].valence}"
                it[happiness] = "${songs[x].happiness}"
            }
            x++
        }
    }
}

fun menuPrincipal(): String{
    
}
