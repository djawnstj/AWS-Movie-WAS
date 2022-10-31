package com.awsmovie.dto.user

data class UserDto(
    val uid: Long?,
    val userName: String = "",
    val userId: String = "",
    val userPw: String = "",
)