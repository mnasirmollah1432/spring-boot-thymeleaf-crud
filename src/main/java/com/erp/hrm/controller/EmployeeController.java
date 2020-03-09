package com.erp.hrm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.erp.hrm.entity.Employee;
import com.erp.hrm.repository.EmployeeRepository;

@Controller
public class EmployeeController {
	
	@Autowired
	 EmployeeRepository employeeRepository;


    @GetMapping("/signup")
    public String showSignUpForm(Employee employee) {
        return "add-employee";
    }
    
    @PostMapping("/addemployee")
    public String addUser(@Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        
        employeeRepository.save(employee);
        
        
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }
    
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id:" + id));
        model.addAttribute("employee", employee);
        return "update-employee";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	employee.setId(id);
            return "update-employee";
        }
        
        employeeRepository.save(employee);
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employeeRepository.delete(employee);
        model.addAttribute("users", employeeRepository.findAll());
        return "index";
    }
}

