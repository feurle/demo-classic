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

/**
 * Controllers provide access to the application behavior that you typically define through a service/repository
 * interface.
 * Controllers interpret user input and transform it into a model that is represented to the user by the view.
 * Spring implements a controller in a very abstract way, which enables you to create a wide variety of controllers.
 * <p>
 * The @Controller annotation indicates that a particular class serves the role of a controller. Spring does not
 * require you to extend any controller base class or reference the Servlet API. However, you can still reference
 * Servlet-specific features if you need to.
 */
@Controller
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customer/new")
    public String initCreationForm(Model model) {
        logger.info("Customer form initialized");
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }

    @PostMapping("/customer/new")
    public String processCreationForm(@ModelAttribute Customer customer, Model model) {
        customerRepository.save(customer);
        logger.info("New customer {} saved", customer);
        model.addAttribute("customer", customer);
        return "redirect:/customer/list";
    }


    @GetMapping("/customer/edit/{customerId}")
    public String initUpdateCustomerForm(@PathVariable("customerId") Long id, Model model) {
        logger.info("Edit customer with id {}", id);
        Customer customer = customerRepository.findCustomerById(id);
        logger.info("Fount customer {}", customer);
        model.addAttribute("customer", customer);
        return "customerForm";
    }


    @PostMapping("/customer/edit/{customerId}")
    public String processUpdateCustomerForm(Customer customer, @PathVariable("customerId") Long id) {
        customer.setId(id);
        customerRepository.save(customer);
        logger.info("Saved customer {}", customer);
        return "redirect:/customer/list";
    }


    @GetMapping("/customer/list")
    public String showAllCustomers(Model model) {
        List<Customer> customerList = customerRepository.findAll();
        logger.info("Found {} customers", customerList.size());
        model.addAttribute("customerList", customerList);
        return "customerList";
    }

    @GetMapping("/customer/delete/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") Long id, Model model) {
        logger.info("Delete customer with id {}", id);
        customerRepository.deleteById(id);
        List<Customer> customerList = customerRepository.findAll();
        model.addAttribute("customerList", customerList);
        return "customerList";
    }


    @GetMapping("/customer/view/{customerId}")
    public String viewCustomer(@PathVariable("customerId") Long id, Model model) {
        Customer customer = customerRepository.findCustomerById(id);
        logger.info("Found customer {}", customer);
        model.addAttribute("customer", customer);
        return "customerDetails";
    }


}
