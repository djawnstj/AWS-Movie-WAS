package com.awsmovie.controller.response

import com.awsmovie.dto.movie.genre.GenreDto
import org.springframework.http.HttpStatus

data class GenreResponse(
    override val code: Int,
    override val status: HttpStatus,
    override val message: String,
    val count: Int,
    val result: List<GenreDto>,
): BaseResponse(code, status, message)