package com.liuzk.prism.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liuzk.prism.data.model.Experience
import com.liuzk.prism.data.repository.ExperienceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ExperienceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false

    init {
        getExperiences()
    }

    private fun getExperiences() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val newExperiences = repository.getExperiences(page = 1)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        experiences = newExperiences
                    )
                }
                currentPage = 1
                isLastPage = newExperiences.isEmpty()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to fetch experiences") }
            }
        }
    }

    fun toggleLike(experience: Experience) {
        _uiState.update { currentState ->
            val updatedExperiences = currentState.experiences.map {
                if (it.id == experience.id) {
                    it.copy(isLiked = !it.isLiked, likes = if (it.isLiked) it.likes - 1 else it.likes + 1)
                } else {
                    it
                }
            }
            currentState.copy(experiences = updatedExperiences)
        }
    }

    fun toggleLayout() {
        _uiState.update { it.copy(isDualColumn = !it.isDualColumn) }
    }

    fun refresh() {
        if (_uiState.value.isRefreshing) return

        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, error = null) }
            try {
                val newExperiences = repository.getExperiences(page = 1)
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        experiences = newExperiences
                    )
                }
                currentPage = 1
                isLastPage = newExperiences.isEmpty()
            } catch (e: Exception) {
                _uiState.update { it.copy(isRefreshing = false, error = "Failed to refresh experiences") }
            }
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (currentState.isLoading || currentState.isLoadingMore || currentState.isRefreshing || isLastPage) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMore = true) }
            try {
                val nextPage = currentPage + 1
                val newExperiences = repository.getExperiences(page = nextPage)
                if (newExperiences.isNotEmpty()) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoadingMore = false,
                            experiences = currentState.experiences + newExperiences
                        )
                    }
                    currentPage = nextPage
                } else {
                    isLastPage = true
                    _uiState.update { it.copy(isLoadingMore = false) } // Stop loading more indicator
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoadingMore = false, error = "Failed to load more experiences") }
            }
        }
    }
}
