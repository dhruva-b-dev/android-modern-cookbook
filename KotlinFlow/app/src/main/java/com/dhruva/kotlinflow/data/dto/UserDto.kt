package com.dhruva.kotlinflow.data.dto

import com.dhruva.kotlinflow.model.UserItem
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    override val id: Int = 0,
    @SerializedName("name")
    override val name: String = "",
    @SerializedName("email")
    override val email: String = "",
    @SerializedName("avatar")
    override val avatar: String = ""
) : UserItem
