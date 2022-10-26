package com.awsmovie.controller.response

import org.springframework.http.HttpStatus

data class ListResponse(
    override val code: Int,
    override val status: HttpStatus,
    override val message: String,
    val count: Int,
    val result: List<Any>,
): BaseResponse(code, status, message)