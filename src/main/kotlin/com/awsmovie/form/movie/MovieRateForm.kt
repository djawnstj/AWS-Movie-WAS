package com.awsmovie.form.movie

import com.awsmovie.form.user.UserForm

data class MovieRateForm(
    val user: UserForm,
    val rate: Int,
    val comment: String
)