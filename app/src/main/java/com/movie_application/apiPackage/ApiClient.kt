package com.movie_application.apiPackage


import com.movie_application.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//STEP1
object ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //retrofit will understand as a converter GSON converter will be used
                .build()

        return retrofit as Retrofit
    }
}