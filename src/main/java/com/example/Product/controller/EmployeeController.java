package com.example.Product.controller;

import com.example.Product.dao.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository eRepo;
    @GetMapping({"/list", "/"})
    public ModelAndView getAllEmployees(){
        ModelAndView mav = new ModelAndView("list-employees");
        mav.addObject("employees", eRepo.findAll());
        return mav;
    }
}
