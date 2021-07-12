package com.campus.democlassic.web;

import com.campus.democlassic.domain.Customer;
import com.campus.democlassic.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customer")
    public String customer(Model model) {
        logger.info("customer get method invoked");
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }

    @GetMapping("/customer/list")
    public String customerList(Model model) {
        List<Customer> customerList = customerRepository.findAll();
        logger.info("found {} customers", customerList.size());
        model.addAttribute("customerList",customerList);
        return "customerList";
    }

    @GetMapping("/customer/delete/{id}")
    public String customerDelete(@PathVariable Long id, Model model) {
        customerRepository.deleteById(id);
        logger.info("delete customer with id {}", id);
        List<Customer> customerList = customerRepository.findAll();
        model.addAttribute("customerList",customerList);
        return "customerList";
    }

    @GetMapping("/customer/view/{id}")
    public String customerDetails(@PathVariable Long id, Model model) {
        Optional<Customer> o = customerRepository.findById(id);
        logger.info("view customer with id {}", id);
        model.addAttribute("customer",o.get());
        return "customerDetails";
    }

    @PostMapping("/customer")
    public String customerSubmit(@ModelAttribute Customer customer, Model model) {
        logger.info("Customer: {}", customer.toString());
        customerRepository.save(customer);
        model.addAttribute("customer", customer);
        return "customerDetails";
    }



}
