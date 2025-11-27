package com.liuzk.prism.ui.main

import com.liuzk.prism.data.model.Experience

data class MainUiState(
    val experiences: List<Experience> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isDualColumn: Boolean = true
)
