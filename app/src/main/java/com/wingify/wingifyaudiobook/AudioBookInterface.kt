package com.wingify.wingifyaudiobook

import retrofit2.Response
import retrofit2.http.GET

interface AudioBookInterface {

    @GET("/v3/2abb5b4e-b46b-4b0d-a7ba-a20eb394782a")
    suspend fun getAudioBookDetails() : Response<AudioBookDetails>
}