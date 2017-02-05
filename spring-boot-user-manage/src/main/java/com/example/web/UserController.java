package com.example.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.model.User;
import com.example.domain.service.LoginUserDetails;
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

        if (result.hasErrors()) {
            return createForm(form);
        }

        User user = User.builder()
                .loginId(form.getLoginId())
                .lastName(form.getLastName())
                .firstName(form.getFirstName())
                .mailAddress(form.getMailAddress())
                .build();

        userService.create(user, form.getPassword());

        return "redirect:/users";
    }

    @RequestMapping(path = "{id}/update", params = "form")
    public String updateForm(
            @PathVariable("id") int userId, @ModelAttribute UserForm form) {

        User user = userService.find(userId);

        form.setId(user.getId());
        form.setLoginId(user.getLoginId());
        form.setLastName(user.getLastName());
        form.setFirstName(user.getFirstName());
        form.setMailAddress(user.getMailAddress());

        return "users/update";
    }

    @PostMapping(path = "{id}/update", params = "confirm")
    public String updateConfirm(@ModelAttribute @Validated UserForm form, BindingResult result) {

        if (result.hasErrors()) {
            return updateRedo(form);
        }

        return "users/updateConfirm";
    }

    @RequestMapping(path = "{id}/update", params = "redo")
    public String updateRedo(@ModelAttribute UserForm form) {

        return "users/update";
    }

    @PostMapping("{id}/update")
    public String update(
            @AuthenticationPrincipal LoginUserDetails userDetails,
            @PathVariable("id") int userId, @ModelAttribute @Validated UserForm form, BindingResult result) {

        if (result.hasErrors()) {
            return updateRedo(form);
        }

        User user = User.builder()
                .id(userId)
                .loginId(form.getLoginId())
                .lastName(form.getLastName())
                .firstName(form.getFirstName())
                .mailAddress(form.getMailAddress())
                .build();

        if (form.isChangePassword()) {
            userService.update(user, form.getPassword());
        } else {
            userService.updateWithoutPassword(user);
        }

        return "redirect:/users";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") int userId) {

        userService.delete(userId);

        return "redirect:/users";
    }
}
