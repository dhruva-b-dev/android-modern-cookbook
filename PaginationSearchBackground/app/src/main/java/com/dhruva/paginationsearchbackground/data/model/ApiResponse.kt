package com.dhruva.paginationsearchbackground.data.model

data class ApiResponse<T>(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: T
)