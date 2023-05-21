package com.example.Product.controller;

import com.example.Product.dao.CompanyRepository;
import com.example.Product.dao.EmployeeRepository;
import com.example.Product.entity.Company;
import com.example.Product.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // Mapping to display the form for adding employees
    @GetMapping("/employees/new")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("companies", companyRepository.findAll());
        return "employee-form";
    }

    // Mapping for the form submission
    @PostMapping("/employees")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam("companyName") String companyName, Model model) {

        // Find or create the company based on the provided company name
        Company foundCompany = companyRepository.findByName(companyName)
                .orElseGet(() -> companyRepository.save(new Company(companyName)));

        // Set the company for the employee
        employee.setCompany(foundCompany);

        // Save the employee to the database
        employeeRepository.save(employee);

        // Add success message to the model
        model.addAttribute("successMessage", "Employee added successfully!");

        // Redirect to the success page
        return "redirect:/success";
    }

    // Mapping to list all employees
    @GetMapping("/employees")
    public ModelAndView getAllEmployees() {
        ModelAndView mav = new ModelAndView("list-employees");
        mav.addObject("employees", employeeRepository.findAll());
        return mav;
    }

    // Mapping for the success page
    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}
