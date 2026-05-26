package com.dhruva.kotlincoroutines.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhruva.kotlincoroutines.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "avatar") val avatar: String?
) {
    fun toDomain(): User = User(
        id = id,
        email = email,
        name = name,
        avatar = avatar
    )

    companion object {
        fun fromDomain(user: User): UserEntity = UserEntity(
            id = user.id,
            email = user.email,
            name = user.name,
            avatar = user.avatar
        )
    }
}