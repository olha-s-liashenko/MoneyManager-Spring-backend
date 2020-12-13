package com.oliash.MoneyManager.controller;

import com.oliash.MoneyManager.model.Expense;
import com.oliash.MoneyManager.service.ExpensesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private final ExpensesService service;

    public ExpenseController(ExpensesService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/expenses/{id}")
    ResponseEntity<Expense> one(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOne(id));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/expenses")
    ResponseEntity<List<Expense>> all() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/expenses")
    ResponseEntity<Expense> newExpense(@RequestBody Expense expense) {
        return ResponseEntity.status(HttpStatus.OK).body(service.createOne(expense));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/expenses/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
