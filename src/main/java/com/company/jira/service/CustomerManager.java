package com.company.jira.service;

import com.company.jira.model.Customer;
import com.company.jira.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerManager {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public ResponseEntity<Iterable<Customer>> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        Iterable<Customer> all = customerRepository.findAll();
        ResponseEntity<Iterable<Customer>> iterableResponseEntity = new ResponseEntity<>(all, HttpStatus.OK);

        return iterableResponseEntity;
    }
}
