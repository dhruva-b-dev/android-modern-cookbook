package com.dhruva.kotlincoroutines.data.remote

import com.dhruva.kotlincoroutines.data.dto.UserDto
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("more-users")
    suspend fun getMoreUsers(): List<UserDto>

    @GET("error")
    suspend fun getUsersWithError(): List<UserDto>

}