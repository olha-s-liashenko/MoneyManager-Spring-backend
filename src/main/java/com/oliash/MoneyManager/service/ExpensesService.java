package com.oliash.MoneyManager.service;

import com.oliash.MoneyManager.exception.ExpenseRecordNotFoundException;
import com.oliash.MoneyManager.model.Expense;
import com.oliash.MoneyManager.repository.ExpenseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesService {

    private final ExpenseRepository repository;

    public ExpensesService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense getOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new ExpenseRecordNotFoundException(id));
    }

    public List<Expense> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public Expense createOne(Expense expense) {
        return repository.save(expense);
    }

    public List<Expense> createMany(List<Expense> expenses) {
        return repository.saveAll(expenses);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
