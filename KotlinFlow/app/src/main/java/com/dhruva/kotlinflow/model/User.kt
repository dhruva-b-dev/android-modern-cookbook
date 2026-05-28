package com.dhruva.kotlinflow.model

data class User(
    override val id: Int,
    override val name: String?,
    override val email: String?,
    override val avatar: String?
) : UserItem