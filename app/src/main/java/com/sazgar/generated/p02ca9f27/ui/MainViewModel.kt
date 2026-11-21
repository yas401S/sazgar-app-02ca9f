package com.sazgar.generated.p02ca9f27.ui

import androidx.lifecycle.ViewModel
import com.sazgar.generated.p02ca9f27.data.AppRepository
import com.sazgar.generated.p02ca9f27.model.AppRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Ready(
        val items: List<AppRecord>,
        val completedCount: Int,
        val totalMetric: Int
    ) : MainUiState
    data class Error(val message: String) : MainUiState
}

class MainViewModel(
    private val repository: AppRepository = AppRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(buildReadyState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun addItem(title: String, category: String = "اصلی", metricValue: Int = 50, note: String = "") {
        repository.addRecord(title = title, category = category, metricValue = metricValue, note = note)
        _uiState.value = buildReadyState()
    }

    fun toggleItem(id: String) {
        repository.toggleCompleted(id)
        _uiState.value = buildReadyState()
    }

    fun deleteItem(id: String) {
        repository.removeRecord(id)
        _uiState.value = buildReadyState()
    }

    private fun buildReadyState(): MainUiState.Ready {
        val current = repository.records.value
        return MainUiState.Ready(
            items = current,
            completedCount = current.count { it.completed },
            totalMetric = current.sumOf { it.metricValue }
        )
    }
}