package com.awsmovie.controller

import com.awsmovie.form.user.LoginForm
import com.awsmovie.form.user.UserForm
import com.awsmovie.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpSession
import javax.validation.Valid

@Controller
class UserController @Autowired constructor(
    private val userService: UserService
) {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("loginForm", LoginForm())
        return "users/login"
    }

    @GetMapping("/users")
    fun login(@Valid form: LoginForm, errors: Errors, session: HttpSession): String {

        if (errors.hasErrors()) {
            return "users/login"
        }

        val response = userService.callLogin(form)

        response?.let {
            if (it.code == 200 && it.count == 1) session.setAttribute("uid", it.result[0].uid ?: -1)
        }

        return "redirect:/"
    }

    @GetMapping("/signup")
    fun signUp(model: Model): String {
        model.addAttribute("userForm", UserForm())
        return "users/signup"
    }

    @PostMapping("/signup")
    fun create(@Valid form: UserForm, result: BindingResult, session: HttpSession): String {

        if (result.hasErrors()) {
            return "users/signup"
        }

        val response = userService.callSignUp(form)

        response.let {
            return "redirect:/"
        }

    }

}