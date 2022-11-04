package com.awsmovie.controller

import com.awsmovie.form.movie.MovieRateForm
import com.awsmovie.service.movie.MovieRateService
import com.awsmovie.util.StringUtil.isNumeric
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpSession

@Controller
@EnableRedisHttpSession
class MovieRateController @Autowired constructor(
    private val movieRateService: MovieRateService
) {

    @PostMapping("/movie-rates")
    fun createRate(movieRate: MovieRateForm, session: HttpSession): String {

        val uid = session.getAttribute("uid").toString()

        check (uid.isNumeric()) { throw IllegalStateException("세션 정보가 잘못되었습니다.") }

        movieRateService.callCreateMovieRate(movieRate, uid)

        return "redirect:/movies/${movieRate.movieId}"
    }

}