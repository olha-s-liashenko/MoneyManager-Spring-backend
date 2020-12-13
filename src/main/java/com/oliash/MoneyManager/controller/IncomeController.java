package com.oliash.MoneyManager.controller;

import com.oliash.MoneyManager.model.Income;
import com.oliash.MoneyManager.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncomeController {

    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/incomes/{id}")
    ResponseEntity<Income> one(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOne(id));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/incomes")
    ResponseEntity<List<Income>> all() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/incomes")
    ResponseEntity<Income> newIncome(@RequestBody Income income) {
        return ResponseEntity.status(HttpStatus.OK).body(service.createOne(income));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/incomes/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
    //TODO: GET last month expenses
    //TODO: GET last year expenses
    //TODO: PUT edit expense
}
