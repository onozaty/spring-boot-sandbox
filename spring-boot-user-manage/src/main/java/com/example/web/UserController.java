package com.example.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.model.User;
import com.example.domain.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("users")
public class UserController {

    private UserService userService;

    @GetMapping
    public String list(Model model) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "users/list";
    }

    @RequestMapping(path = "create", params = "form")
    public String createForm(@ModelAttribute UserForm form) {

        return "users/create";
    }

    @PostMapping(path = "create", params = "confirm")
    public String createConfirm(@ModelAttribute @Validated UserForm form, BindingResult result) {

        if (result.hasErrors()) {
            return createForm(form);
        }

        return "users/createConfirm";
    }

    @PostMapping("create")
    public String create(@ModelAttribute @Validated UserForm form, BindingResult result) {

        User user = User.builder()
                .loginId(form.getLoginId())
                .lastName(form.getLastName())
                .firstName(form.getFirstName())
                .mailAddress(form.getMailAddress())
                .build();

        userService.create(user, form.getPassword());

        return "redirect:/users";
    }
}
