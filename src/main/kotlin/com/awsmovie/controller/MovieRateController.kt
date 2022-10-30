package com.awsmovie.controller

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.form.movie.MovieRateForm
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

        val userId = session.getAttribute("userId")

        println(userId)


        val response = webClient.post()
            .uri("/movie-rates")
            .body(Mono.just(movieRate), MovieRateForm::class.java)
            .retrieve()
            .bodyToFlux(BaseResponse::class.java)
            .blockFirst()

        println(response)

        return "redirect:/"
    }

}