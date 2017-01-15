package com.example.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.model.Customer;
import com.example.domain.service.CustomerService;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String list(Model model) {

        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);

        return "customers/list";
    }

    @GetMapping(path = "new")
    public String createForm(@ModelAttribute CustomerForm form) {

        return "customers/new";
    }

    @PostMapping("create")
    public String create(@ModelAttribute @Validated CustomerForm form, BindingResult result) {

        if (result.hasErrors()) {
            return createForm(form);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);

        customerService.create(customer);

        return "redirect:/customers";
    }

    @GetMapping(path = "{id}/edit")
    public String edit(@PathVariable("id") Integer id, @ModelAttribute CustomerForm form) {

        Customer customer = customerService.findOne(id);
        BeanUtils.copyProperties(customer, form);

        return "customers/edit";
    }

    @PostMapping(path = "{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated CustomerForm form,
            BindingResult result) {

        if (result.hasErrors()) {
            return edit(id, form);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customerService.update(customer);

        return "redirect:/customers";
    }

    @GetMapping(path = "{id}/delete")
    public String delete(@PathVariable("id") Integer id) {

        customerService.delete(id);
        return "redirect:/customers";
    }
}
