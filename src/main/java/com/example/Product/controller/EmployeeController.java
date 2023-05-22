package com.example.Product.controller;

import com.example.Product.dao.CompanyRepository;
import com.example.Product.dao.EmployeeRepository;
import com.example.Product.dao.EventRepository;
import com.example.Product.entity.Company;
import com.example.Product.entity.Employee;
import com.example.Product.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EventRepository eventRepository;

    // Mapping to display the form for adding employees
    @GetMapping("/employees/new")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("companies", companyRepository.findAll());
        return "list-employees";
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
    @PostMapping("/employees/delete")
    public String deleteEmployee(@RequestParam("id") Long id) {
        // Delete the employee from the database
        employeeRepository.deleteById(id);

        // Redirect to the list of employees
        return "redirect:/employees";
    }

    // Mapping to display the form for adding events
    @GetMapping("/events/new")
    public String showEventForm(Model model) {
        Event event = new Event();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("event", event);
        model.addAttribute("companies", companies);
        return "list-events";
    }

    // Mapping for the event form submission
    @PostMapping("/events")
    public String addEvent(@ModelAttribute Event event, @RequestParam("companyName") String companyName, Model model) {

        // Find or create the company based on the provided company name
        Company foundCompany = companyRepository.findByName(companyName)
                .orElseGet(() -> companyRepository.save(new Company(companyName)));

        // Set the company for the event
        event.setCompany(foundCompany);

        // Save the event to the database
        eventRepository.save(event);

        // Redirect to a success page or return a response indicating success
        return "redirect:/success";
    }

    // Mapping to display the list of events
    @GetMapping("/events")
    public ModelAndView getAllEvents() {
        ModelAndView mav = new ModelAndView("list-events");
        mav.addObject("events", eventRepository.findAll());
        return mav;
    }
}