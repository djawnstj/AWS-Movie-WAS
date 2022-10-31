package com.awsmovie.controller

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.dto.movie.MovieRateDto
import com.awsmovie.dto.user.UserDto
import com.awsmovie.form.movie.MovieRateForm
import com.awsmovie.util.StringUtil.isNumeric
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import javax.servlet.http.HttpSession

@Controller
@EnableRedisHttpSession
class MovieRateController @Autowired constructor(
    private val webClient: WebClient
) {

    @PostMapping("/movie-rates")
    fun createRate(movieRate: MovieRateForm, session: HttpSession): String {

        val uid = session.getAttribute("uid").toString()

        check (uid.isNumeric()) { throw IllegalStateException("세션 정보가 잘못되었습니다.") }

        movieRate.apply {
            val movieRateDto = MovieRateDto(
                UserDto(uid.toLong(), "", "", ""),
                movieRate.movieId,
                rate.toDouble()/2,
                comment
            )

            val response = webClient.post()
                .uri("/movie-rates")
                .body(Mono.just(movieRateDto), MovieRateForm::class.java)
                .retrieve()
                .bodyToFlux(BaseResponse::class.java)
                .blockFirst()

            return "redirect:/"
        }
    }

}