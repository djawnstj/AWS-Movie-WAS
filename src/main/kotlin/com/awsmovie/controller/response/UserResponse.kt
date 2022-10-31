package com.awsmovie.controller.response

import com.awsmovie.dto.user.UserDto
import org.springframework.http.HttpStatus

data class UserResponse(
    override val code: Int,
    override val status: HttpStatus,
    override val message: String,
    val count: Int,
    val result: List<UserDto>,
): BaseResponse(code, status, message)