package com.awsmovie.controller

import com.awsmovie.form.movie.MovieForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.reactive.function.client.*
import javax.validation.Valid

@Controller
class MovieController @Autowired constructor(
    private val webClient: WebClient
) {

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

       println(form)

        return "redirect:/"
    }

}