package com.awsmovie.service.user

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.controller.response.UserResponse
import com.awsmovie.form.user.LoginForm
import com.awsmovie.form.user.UserForm
import com.awsmovie.util.ParamBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class UserService @Autowired constructor(
    private val webClient: WebClient
) {

    fun callLogin(loginForm: LoginForm): UserResponse? {

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()

        params.add("userId", loginForm.userId)
        params.add("userPw", loginForm.userPw)

        return webClient.get()
            .uri(ParamBuilder.createUri("/users", params))
            .retrieve()
            .bodyToFlux(UserResponse::class.java)
            .blockFirst()

    }

    fun callSignUp(userForm: UserForm): BaseResponse? = webClient.post()
            .uri("/users")
            .body(Mono.just(userForm), UserForm::class.java)
            .retrieve()
            .bodyToFlux(BaseResponse::class.java)
            .blockFirst()

}