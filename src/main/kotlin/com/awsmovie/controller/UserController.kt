package com.awsmovie.controller

import com.awsmovie.form.UserForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class UserController {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("userForm", UserForm())
        return "users/login"
    }

    @GetMapping("/users")
    fun login(@Valid form: UserForm, errors: Errors): String {
        if (errors.hasErrors()) {
            return "users/login"
        }

        return "redirect:/"
    }

    @GetMapping("/signup")
    fun signUp(model: Model): String {
        model.addAttribute("userForm", UserForm())
        return "users/signup"
    }

    @PostMapping("/users/new")
    fun create(@Valid form: UserForm, result: BindingResult): String {
        if (result.hasErrors()) {
            return "users/signup"
        }

        return "redirect:/"
    }

}