package com.oliash.MoneyManager.service;

import com.oliash.MoneyManager.exception.ExpenseRecordNotFoundException;
import com.oliash.MoneyManager.model.Expense;
import com.oliash.MoneyManager.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestExpensesService {

    @Autowired
    private ExpenseRepository repository;

    private ExpensesService service;

    @BeforeEach
    void prerequisites() {
        repository.deleteAll();
        service = new ExpensesService(repository);
    }

    @Test
    void testGetEmptyExpenses() {
        assertTrue(repository.findAll().isEmpty());
        Long id = 1L;
        assertThrows(ExpenseRecordNotFoundException.class, () -> service.getOne(id));
    }

    @Test
    void testGetOneExpense() {
        Expense expense = new Expense(new BigDecimal("530.00"), "Apartment rent", LocalDate.of(2020, Calendar.SEPTEMBER, 10));
        repository.save(expense);
        assertEquals(expense, service.getOne(expense.getId()));
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(
                new BigDecimal("530.00"),
                "Apartment rent",
                LocalDate.of(2020, Calendar.SEPTEMBER, 10)));
        expenses.add(new Expense(
                new BigDecimal("55.90"),
                "Grocery",
                LocalDate.of(2020, Calendar.SEPTEMBER, 15)));
        repository.saveAll(expenses);

        List<Expense> retrievedExpenses = service.getAll();
        assertEquals(expenses, retrievedExpenses);
        assertEquals(expenses.size(), retrievedExpenses.size());
    }

    @Test
    void testCreateExpense() {
        Expense expenseToSave = new Expense(new BigDecimal("530.00"), "Apartment rent", LocalDate.of(2020, Calendar.SEPTEMBER, 10));
        assertEquals(expenseToSave, service.createOne(expenseToSave));
    }

    @Test
    void testCreateManyExpenses() {
        List<Expense> expensesToSave = new ArrayList<>();
        expensesToSave.add(new Expense(
                new BigDecimal("530.00"),
                "Apartment rent",
                LocalDate.of(2020, Calendar.SEPTEMBER, 10)));
        expensesToSave.add(new Expense(
                new BigDecimal("55.90"),
                "Grocery",
                LocalDate.of(2020, Calendar.SEPTEMBER, 15)));
        expensesToSave.add(new Expense(
                new BigDecimal("22.34"),
                "Gas station",
                LocalDate.now()));

        List<Expense> savedExpenses = service.createMany(expensesToSave);
        assertEquals(expensesToSave, savedExpenses);
    }

    @Test
    void testDeleteExpense() {
        Expense expense = new Expense(new BigDecimal("530.00"), "Apartment rent", LocalDate.of(2020, Calendar.SEPTEMBER, 10));
        repository.save(expense);
        service.delete(expense.getId());
        assertFalse(repository.existsById(expense.getId()));
    }
}
