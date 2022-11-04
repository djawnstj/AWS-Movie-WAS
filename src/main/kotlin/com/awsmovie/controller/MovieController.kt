package com.awsmovie.controller

import com.awsmovie.form.movie.MovieForm
import com.awsmovie.service.movie.MovieService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid


@Controller
class MovieController @Autowired constructor(
    private val movieService: MovieService
) {

    @ModelAttribute("genres")
    fun genres(): Map<Int, String> = movieService.callGenres()

    @GetMapping("/movies/create-form")
    fun createForm(model: Model): String {
        model.addAttribute("movieForm", MovieForm())
        return "movies/create-movie"
    }

    @PostMapping("/movies/new")
    fun createMovie(@Valid form: MovieForm, result: BindingResult): String {

        if (result.hasErrors()) {
            return "/movies/create-movie"
        }

        movieService.callCreateMovie(form)

        return "redirect:/"
    }

    @GetMapping("/movies")
    fun movies(model: Model): String {

        val response = movieService.callMovieList()

        response?.result?.let { model.addAttribute("movies", it) }

        return "movies/movies"

    }

    @GetMapping("/movies/{id}")
    fun movieDetail(@PathVariable("id") movieId: Long, model: Model): String {

        val response = movieService.callMovieDetail(movieId)

        response?.apply {
            if (count >= 1) model.addAttribute("movie", result[0])
        }

        return "movies/movie-detail"
    }

}