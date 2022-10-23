package com.awsmovie.form.movie

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MovieForm(
    @field: NotEmpty(message = "영화 이름은 필수 입니다.")
    var movieName: String = "",
    @field: NotNull(message = "상영 시간은 필수 입니다.")
    var runTime: Int? = null,
    @field: NotEmpty(message = "개봉일은 필수 입니다.")
    var openingDate: String = "",
    var summary: String = "",
    @field: NotEmpty(message = "장르 선택은 필수 입니다.")
    var genres: List<Int> = ArrayList(),
    var movieImage: MultipartFile? = null,
    var movieImagePath: String? = null,
    var rates: List<MovieRateForm> = ArrayList(),
) {

    override fun toString(): String {
        return "MovieForm(movieName='$movieName', runTime=$runTime,\n openingDate='$openingDate', summary='$summary',\n genres=$genres, movieImage=$movieImage,\n movieImagePath=$movieImagePath, rates=$rates)"
    }
}