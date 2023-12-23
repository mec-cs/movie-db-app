package com.movie_application.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.movie_application.constants.Constants
import java.io.Serializable

@Entity(tableName = Constants.TABLE_NAME)
class Movie
    (
    @SerializedName("ComingSoon")
    val comingSoon: Boolean = false,
    @SerializedName("Title")
    @PrimaryKey
    val title: String = "",
    @SerializedName("Year")
    val year: String = "",
    @SerializedName("Rated")
    val rated: String = "",
    @SerializedName("Released")
    val releaseDate: String = "",
    @SerializedName("Runtime")
    val duration: String = "",
    @SerializedName("Genre")
    val genre: String = "",
    @SerializedName("Director")
    val director: String = "",
    @SerializedName("Writer")
    val writer: String = "",
    @SerializedName("Actors")
    val actors: String = "",
    @SerializedName("Plot")
    val plot: String = "",
    @SerializedName("Language")
    val languages: String = "",
    @SerializedName("Country")
    val countryList: String = "",
    @SerializedName("Awards")
    val awards: String = "",
    @SerializedName("Poster")
    val posterImgLink: String = "",
    @SerializedName("Metascore")
    val metascore: String = "",
    @SerializedName("imdbRating")
    val imdbRating: String = "",
    @SerializedName("imdbVotes")
    val imdbVotes: String = "",
    @SerializedName("imdbID")
    val imdbID: String = "",
    @SerializedName("Type")
    val type: String = "",
    @SerializedName("Response")
    val response: String = "",
    @SerializedName("Images")
    val imagesList: ArrayList<String> = ArrayList()
) : Serializable
{
    // title, year, release date, duration, director, writer, actors, plot, languages, awards, metascore, imdbrating, imdbId, type
    override fun toString(): String {
        return  "Title: " +title+"\n"+
                "Year: "+year+"\n"+
                "Duration: " +duration+"\n"+
                "Director: " +director+"\n"+
                "Writer: " +writer+"\n"+
                "Actors: " +actors+"\n"+
                "Plot: " +plot+"\n"+
                "Languages: " +languages+"\n"+
                "Awards: "+awards+"\n"+
                "Metascore: " +metascore+"\n"+
                "IMDB Rating: " +imdbRating+"\n"+
                "IMDB Id: " +imdbID+"\n"+
                "Type: " +type+"\n"

    }
}