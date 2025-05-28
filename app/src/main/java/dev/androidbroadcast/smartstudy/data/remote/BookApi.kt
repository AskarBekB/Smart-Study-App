package dev.androidbroadcast.smartstudy.data.remote

import dev.androidbroadcast.smartstudy.data.local.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi{
    @GET("volumes")
    suspend fun getBooksBySubject(
        @Query("q") subject: String = "subject:study",
        @Query("maxResults") max: Int = 20
    ): BookResponse
}