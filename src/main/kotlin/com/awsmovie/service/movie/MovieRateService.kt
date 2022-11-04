package com.awsmovie.service.movie

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.dto.movie.MovieRateDto
import com.awsmovie.dto.user.UserDto
import com.awsmovie.form.movie.MovieRateForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class MovieRateService @Autowired constructor(
    private val webClient: WebClient
) {

    fun callCreateMovieRate(movieRate: MovieRateForm, uid: String): BaseResponse? {

        movieRate.apply {
            val movieRateDto = MovieRateDto(
                null,
                UserDto(uid.toLong(), "", "", ""),
                movieRate.movieId,
                rate.toDouble() / 2,
                comment
            )

            return webClient.post()
                .uri("/movie-rates")
                .body(Mono.just(movieRateDto), MovieRateForm::class.java)
                .retrieve()
                .bodyToFlux(BaseResponse::class.java)
                .blockFirst()
        }
    }

}