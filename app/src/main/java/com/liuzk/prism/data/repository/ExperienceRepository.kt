package com.liuzk.prism.data.repository

import com.liuzk.prism.data.model.Experience
import com.liuzk.prism.data.remote.ApiService
import javax.inject.Inject

class ExperienceRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getExperiences(page: Int): List<Experience> {
        return apiService.getExperiences(page = page)
    }

}
