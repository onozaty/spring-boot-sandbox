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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.model.Customer;
import com.example.domain.service.CustomerService;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ModelAttribute
    public CustomerForm setupForm() {
        return new CustomerForm();
    }

    @GetMapping
    public String list(Model model) {

        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);

        return "customers/list";
    }

    @PostMapping("create")
    public String create(@Validated CustomerForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return list(model);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);

        customerService.create(customer);

        return "redirect:/customers";
    }

    @GetMapping(path = "edit", params = "form")
    public String editForm(@RequestParam Integer id, CustomerForm form) {

        Customer customer = customerService.findOne(id);
        BeanUtils.copyProperties(customer, form);

        return "customers/edit";
    }

    @PostMapping(path = "edit")
    public String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result) {

        if (result.hasErrors()) {
            return editForm(id, form);
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customer.setId(id);
        customerService.update(customer);

        return "redirect:/customers";
    }

    @RequestMapping(path = "edit", params = "goToTop")
    public String goToTop() {
        return "redirect:/customers";
    }

    @PostMapping(path = "delete")
    public String delete(@RequestParam Integer id) {

        customerService.delete(id);
        return "redirect:/customers";
    }
}
