package main

import com.github.kittinunf.fuel.Fuel
import main.dbsongsmodels.FavSongs
import main.dbsongsmodels.Songs
import main.dbsongsmodels.Songs.acusticness
import main.dbsongsmodels.Songs.artistGender
import main.dbsongsmodels.Songs.country
import main.dbsongsmodels.Songs.danceability
import main.dbsongsmodels.Songs.energy
import main.dbsongsmodels.Songs.groupOrSolo
import main.dbsongsmodels.Songs.happiness
import main.dbsongsmodels.Songs.isFinal
import main.dbsongsmodels.Songs.isSongInEnglish
import main.dbsongsmodels.Songs.key
import main.dbsongsmodels.Songs.liveness
import main.dbsongsmodels.Songs.loudness
import main.dbsongsmodels.Songs.mode
import main.dbsongsmodels.Songs.normalizedPoints
import main.dbsongsmodels.Songs.place
import main.dbsongsmodels.Songs.points
import main.dbsongsmodels.Songs.region
import main.dbsongsmodels.Songs.song
import main.dbsongsmodels.Songs.speechiness
import main.dbsongsmodels.Songs.tempo
import main.dbsongsmodels.Songs.timeSignature
import main.dbsongsmodels.Songs.valence
import main.dbsongsmodels.Songs.year
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction



fun main(args: Array<String>) {
    readAndSetData()
    var op: String = ""
    while (op != "4"){
        print(menuPrincipal())
        op = readLine()!!
        when(op){
            "1" -> {
                print("\nIngrese nombre de la cancion: ")
                var songName = readLine()!!
                searchSongName(songName)
            }
            "2" -> {
                print("\nIngrese nombre del artista: ")
                var artistName = readLine()!!
                searchArtistName(artistName)
            }
            "3" -> {
                showFavSongs()
            }
            "4" -> println("La aplicacion se cerrara.")
            else -> println("La opcion ingresada no es valida")
        }
    }

}

fun readAndSetData(){//Lee la informacion desde la API y la guarda localmente

   val songs = readSongs()

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
                it[year] = songs[x].year
                it[country] = songs[x].country
                it[region] = songs[x].region
                it[artistName] = songs[x].artistName
                it[song] = songs[x].song
                it[artistGender] = songs[x].artistGender
                it[groupOrSolo] = songs[x].groupOrSolo
                it[place] = songs[x].place
                it[points] = songs[x].points
                it[isFinal] = songs[x].isFinal
                it[isSongInEnglish] = songs[x].isSongInEnglish
                it[normalizedPoints] = songs[x].normalizedPoints
                it[energy] = songs[x].energy
                it[duration] = songs[x].duration
                it[acusticness] = "${songs[x].acusticness}"
                it[danceability] = songs[x].danceability
                it[tempo] = songs[x].tempo
                it[speechiness] = songs[x].speechiness
                it[key] = songs[x].key
                it[liveness] = songs[x].liveness
                it[timeSignature] = songs[x].timeSignature
                it[mode] = songs[x].mode
                it[loudness] = songs[x].loudness
                it[valence] = songs[x].valence
                it[happiness] = songs[x].happiness
            }
            x++
        }
    }
}

fun menuPrincipal(): String{
    return """

        Menu principal
        1. Buscar canciones por nombre
        2. Buscar canciones por artista
        3. Mostrar todas mis canciones favoritas
        3. Salir
        >>
    """.trimIndent()
}

fun searchSongName(songName: String) {

    //Conceta con la db local
    Database.connect(
            "jdbc:postgresql:songs",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )

    //Agrega los registros a la db y crea las tablas si no existen
    transaction {
        var x = 0
        for (song in Songs.select { Songs.song.like("%${songName}%")}){
            println("${x + 1}. " + song.get(Songs.song))
            x++
        }
    }

    print("\nSeleccione una cancion, sera agregada a favoritos: ")
    var select = readLine()!!
    var selected = select.toInt() - 1
    transaction {
        var foundSongs: List<songsModels.Songs> = Songs.select { Songs.song.like("%$songName%") }.map { songsModels.Songs(it[year], it[country], it[region], it[Songs.artistName], it[song], it[artistGender], it[groupOrSolo], it[place], it[points], it[isFinal], it[isSongInEnglish], it[normalizedPoints], it[energy], it[Songs.duration], it[acusticness], it[danceability], it[tempo], it[speechiness], it[key], it[liveness], it[timeSignature], it[mode], it[loudness], it[valence], it[happiness]) }
        addFavSong(foundSongs[selected])
    }

}

fun searchArtistName(artistName: String) {

    //Conceta con la db local
    Database.connect(
            "jdbc:postgresql:songs",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )

    //Agrega los registros a la db y crea las tablas si no existen
    transaction {
        var x = 0
        for (song in Songs.select { Songs.artistName.like("%${artistName}%")}){
            println("${x + 1}. " + song.get(Songs.artistName))
            x++
        }
    }

    print("\nSeleccione un artista, sera agregada a favoritos: ")
    var select = readLine()!!
    var selected = select.toInt() - 1
    transaction {
        var foundSongs: List<songsModels.Songs> = Songs.select { Songs.artistName.like("%$artistName%") }.map { songsModels.Songs(it[year], it[country], it[region], it[Songs.artistName], it[song], it[artistGender], it[groupOrSolo], it[place], it[points], it[isFinal], it[isSongInEnglish], it[normalizedPoints], it[energy], it[Songs.duration], it[acusticness], it[danceability], it[tempo], it[speechiness], it[key], it[liveness], it[timeSignature], it[mode], it[loudness], it[valence], it[happiness]) }
        addFavSong(foundSongs[selected])
    }
}

fun addFavSong(ssong: songsModels.Songs){
    //Conceta con la db local
    Database.connect(
            "jdbc:postgresql:songs",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )
    transaction {
        SchemaUtils.create(FavSongs)
        FavSongs.insert {
            it[year] = ssong.year
            it[country] = ssong.country
            it[region] = ssong.region
            it[artistName] = ssong.artistName
            it[song] = ssong.song
            it[artistGender] = ssong.artistGender
            it[groupOrSolo] = ssong.groupOrSolo
            it[place] = ssong.place
            it[points] = ssong.points
            it[isFinal] = ssong.isFinal
            it[isSongInEnglish] = ssong.isSongInEnglish
            it[normalizedPoints] = ssong.normalizedPoints
            it[energy] = ssong.energy
            it[duration] = ssong.duration
            it[acusticness] = ssong.acusticness
            it[danceability] = ssong.danceability
            it[tempo] = ssong.tempo
            it[speechiness] = ssong.speechiness
            it[key] = ssong.key
            it[liveness] = ssong.liveness
            it[timeSignature] = ssong.timeSignature
            it[mode] = ssong.mode
            it[loudness] = ssong.loudness
            it[valence] = ssong.valence
            it[happiness] = ssong.happiness
        }
    }
}

fun showFavSongs(){
    //Conceta con la db local
    Database.connect(
            "jdbc:postgresql:songs",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )
    transaction {
        var foundSongs: List<songsModels.Songs>  = FavSongs.selectAll().map { songsModels.Songs(it[year], it[country], it[region], it[Songs.artistName], it[song], it[artistGender], it[groupOrSolo], it[place], it[points], it[isFinal], it[isSongInEnglish], it[normalizedPoints], it[energy], it[Songs.duration], it[acusticness], it[danceability], it[tempo], it[speechiness], it[key], it[liveness], it[timeSignature], it[mode], it[loudness], it[valence], it[happiness]) }
        foundSongs.forEach { println(it) }
    }

}

fun readSongs(): Array<songsModels.Songs>?{
    //Lee la informacion
    val url = "https://next.json-generator.com/api/json/get/EkeSgmXycS"
    val (request, response, result) = Fuel.get(url).responseObject(songsModels.Songs.SongsArrayDeserializer())
    val (songs, err) = result
    return songs
}