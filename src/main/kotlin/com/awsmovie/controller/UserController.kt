package com.awsmovie.controller

import com.awsmovie.controller.response.BaseResponse
import com.awsmovie.form.user.LoginForm
import com.awsmovie.form.user.UserForm
import com.awsmovie.util.ParamBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import javax.validation.Valid

@Controller
class UserController @Autowired constructor(
    private val webClient: WebClient
) {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("loginForm", LoginForm())
        return "users/login"
    }

    @GetMapping("/users")
    fun login(@Valid form: LoginForm, errors: Errors): String {
        if (errors.hasErrors()) {
            return "users/login"
        }

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()

        params.add("userId", form.userId)
        params.add("userPw", form.userPw)

        val bodyToMono = webClient.get()
            .uri(ParamBuilder.createUri("/users", params))
            .retrieve()
            .bodyToMono(String::class.java)
            .subscribe { println(it) }

        return "redirect:/"
    }

    @GetMapping("/signup")
    fun signUp(model: Model): String {
        model.addAttribute("userForm", UserForm())
        return "users/signup"
    }

    @PostMapping("/signup")
    fun create(@Valid form: UserForm, result: BindingResult): String {
        if (result.hasErrors()) {
            return "users/signup"
        }

        val response = webClient.post()
            .uri("/users")
            .body(Mono.just(form), UserForm::class.java)
            .retrieve()
            .bodyToFlux(BaseResponse::class.java)
            .blockFirst()

        response.let {
            println(it?.code)

            return "redirect:/"
        }

    }

}