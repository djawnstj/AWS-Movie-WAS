package com.awsmovie.controller.response

import org.springframework.http.HttpStatus

open class BaseResponse(
    open val code: Int,
    open val status: HttpStatus,
    open val message: String,
)