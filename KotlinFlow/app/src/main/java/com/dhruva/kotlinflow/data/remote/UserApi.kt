package com.dhruva.kotlinflow.data.remote

import com.dhruva.kotlinflow.data.dto.UserDto
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("more-users")
    suspend fun getMoreUsers(): List<UserDto>

    @GET("error")
    suspend fun getUsersWithError(): List<UserDto>

}