package com.sazgar.generated.p02ca9f27.data

import com.sazgar.generated.p02ca9f27.model.AppPriority
import com.sazgar.generated.p02ca9f27.model.AppRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppRepository {
    private val _records = MutableStateFlow(
        listOf(
            AppRecord(
                id = "1",
                title = "اپلیکیشن طراحی‌شده این تص",
                category = "اصلی",
                metricValue = 120,
                priority = AppPriority.HIGH,
                note = "ماژول کلیدی آماده توسعه"
            ),
            AppRecord(
                id = "2",
                title = "یر رابط کاربری را با دقت کامل تحلیل کن",
                category = "گزارش",
                metricValue = 85,
                priority = AppPriority.MEDIUM,
                note = "پایش عملکرد و وضعیت",
                completed = true
            )
        )
    )
    val records: StateFlow<List<AppRecord>> = _records.asStateFlow()

    fun addRecord(
        title: String,
        category: String = "اصلی",
        metricValue: Int = 50,
        note: String = "",
        extraFields: Map<String, String> = emptyMap()
    ) {
        val clean = title.trim()
        if (clean.isEmpty()) return
        val nextId = (_records.value.size + 1).toString() + "_" + System.currentTimeMillis()
        val item = AppRecord(
            id = nextId,
            title = clean,
            category = category.trim().ifBlank { "اصلی" },
            metricValue = metricValue.coerceAtLeast(0),
            note = note.trim(),
            extraFields = extraFields
        )
        _records.value = listOf(item) + _records.value
    }

    fun toggleCompleted(id: String) {
        _records.value = _records.value.map { item ->
            if (item.id == id) item.copy(completed = !item.completed) else item
        }
    }

    fun removeRecord(id: String) {
        _records.value = _records.value.filterNot { it.id == id }
    }
}