package com.awsmovie.form.user

import javax.validation.constraints.NotEmpty

data class LoginForm(
    @field: NotEmpty(message = "ID 값은 필수 입니다.")
    var userId: String = "",
    @field: NotEmpty(message = "PW 값은 필수 입니다.")
    var userPw: String = "",
)