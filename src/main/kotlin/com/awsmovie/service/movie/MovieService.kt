package com.awsmovie.service.movie

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.controller.response.GenreResponse
import com.awsmovie.controller.response.MovieResponse
import com.awsmovie.dto.movie.MovieDto
import com.awsmovie.form.movie.MovieForm
import com.awsmovie.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime

@Service
class MovieService @Autowired constructor(
    private val webClient: WebClient
) {

    fun callGenres(): Map<Int, String> {
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

    fun callCreateMovie(form: MovieForm): BaseResponse? {

        val builder = MultipartBodyBuilder()

        form.apply {
            builder.part("movieDto", MovieDto(
                movieName = movieName,
                runTime = runTime ?: 0,
                openingDate = LocalDateTime.parse("$openingDate 00:00:00", DateUtil.formatter2),
                genreCode = genres,
                summary = summary,
            )
            )
            genres.forEach {
                builder.part("genreCode", it.toString())
                    .contentType(MediaType.APPLICATION_JSON)
            }
            movieImage?.let {
                builder.part("image", it.resource)
            }
        }

        return webClient.post()
            .uri("/movies")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToFlux(BaseResponse::class.java)
            .blockFirst()
    }

    fun callMovieList(): MovieResponse? {

        return webClient.get()
            .uri("/movies")
            .retrieve()
            .bodyToFlux(MovieResponse::class.java)
            .blockFirst()
    }

    fun callMovieDetail(movieId: Long): MovieResponse? = webClient.get()
            .uri("/movies/$movieId")
            .retrieve()
            .bodyToFlux(MovieResponse::class.java)
            .blockFirst()

}