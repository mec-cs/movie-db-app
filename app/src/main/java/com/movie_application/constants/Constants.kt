package com.movie_application.constants

object Constants {
    //for retrofit be sure that at the end of URL there is / character, otherwise url "must end in /" exception is taken
    var BASE_URL: String = "https://run.mocky.io/v3/c46122d9-9a2d-4712-aa57-e5f2949998f5/"


    const val TABLE_NAME = "MOVIES"
    const val DATABASE_NAME = "MOVIES_DB"

    const val COM_TABLE_NAME = "COMMENTS"
    const val COM_DATABASE_NAME = "COMMENTS_DB"
}