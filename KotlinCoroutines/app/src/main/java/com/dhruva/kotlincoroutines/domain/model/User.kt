package com.dhruva.kotlincoroutines.domain.model

data class User(
    val id: Int,
    val name: String?,
    val email: String?,
    val avatar: String?
)