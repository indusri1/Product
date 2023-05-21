package com.example.Product.controller;

import com.example.Product.dao.EmployeeRepository;
import com.example.Product.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    // Mapping to display the form for adding employees
    @GetMapping("/employees/new")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    // Mapping for the form submission
    @PostMapping("/employees")
    public String addEmployee(@ModelAttribute Employee employee) {
        // Save the employee to the database
        employeeRepository.save(employee);

        // Redirect to a success page or return a response indicating success
        return "redirect:/success";
    }

    // Mapping to list all employees
    @GetMapping("/employees")
    public ModelAndView getAllEmployees() {
        ModelAndView mav = new ModelAndView("list-employees");
        mav.addObject("employees", employeeRepository.findAll());
        return mav;
    }
}


