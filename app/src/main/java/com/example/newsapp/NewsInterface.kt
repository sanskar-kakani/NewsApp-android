package com.example.newsapp


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://newsapi.org/v2/top-headlines?country=in&apiKey=b2b02bab5aea4a8086198900e2fb3295

private const val BASE_URL = "https://newsapi.org"

interface NewsInterface {

    //first query start with ?Query then add query with &
    @GET("/v2/top-headlines?apiKey=b2b02bab5aea4a8086198900e2fb3295") //query use to add query in url eg- &country=in
    fun getNews(@Query("country") country:String, @Query("category") category: String): Call<News>


}

//singleton
object NewsService{
    val newsInterface:NewsInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInterface = retrofit.create(NewsInterface::class.java)
    }

}
