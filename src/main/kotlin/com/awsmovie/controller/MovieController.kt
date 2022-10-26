package com.awsmovie.controller

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.controller.response.GenreResponse
import com.awsmovie.dto.movie.MovieDto
import com.awsmovie.form.movie.MovieForm
import com.awsmovie.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime
import javax.validation.Valid


@Controller
class MovieController @Autowired constructor(
    private val webClient: WebClient
) {

    @ModelAttribute("genres")
    fun genres(): Map<Int, String> {
        val genres: MutableMap<Int, String> = LinkedHashMap()

        val response = webClient.get()
            .uri("/genres")
            .retrieve()
            .bodyToFlux(GenreResponse::class.java)
            .blockFirst()

        response?.result?.forEach {
            it.apply { genres[genreCode] = genreKrName }
        }

        return genres
    }

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

        val builder = MultipartBodyBuilder()

        form.apply {
            builder.part("movieDto", MovieDto(
                movieName = movieName,
                runTime = runTime ?: 0,
                openingDate = LocalDateTime.parse("$openingDate 00:00:00", DateUtil.formatter2),
                genreCode = genres,
                summary = summary,
            ))
            genres.forEach {
                builder.part("genreCode", it)
                    .contentType(MediaType.APPLICATION_JSON)
            }
            movieImage?.let {
                builder.part("image", it.resource)
            }
        }

        val response = webClient.post()
            .uri("/movies")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToFlux(BaseResponse::class.java)
            .blockFirst()

        response?.let { println(it) }

        return "redirect:/"
    }

}