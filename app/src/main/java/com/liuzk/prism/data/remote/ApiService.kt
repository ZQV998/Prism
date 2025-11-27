package com.liuzk.prism.data.remote

import com.liuzk.prism.data.model.Experience
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/list")
    suspend fun getExperiences(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): List<Experience>

}
