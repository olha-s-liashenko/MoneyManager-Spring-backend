package com.oliash.MoneyManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliash.MoneyManager.model.Expense;
import com.oliash.MoneyManager.repository.ExpenseRepository;
import com.oliash.MoneyManager.service.ExpensesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestExpensesController {

    @Autowired
    private ExpenseController controller;

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ExpensesService service;

    @BeforeEach
    void prerequisites() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void shouldReturnOneExpense() throws Exception {
        Expense expense = new Expense(new BigDecimal("15.66"), "Buy a new canvas", LocalDate.now());
        repository.save(expense);

        this.mockMvc.perform(get("/expenses/{id}", expense.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expense)));
    }

    @Test
    void shouldReturnAllExpenses() throws Exception {
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

        this.mockMvc.perform(get("/expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expenses)));
    }

    @Test
    void shouldSaveExpenseAndReturn() throws Exception {
        Expense expense = new Expense(new BigDecimal("15.66"), "Buy a new canvas", LocalDate.now());
        expense.setId(1L);
        when(service.createOne(any())).thenReturn(expense);

        this.mockMvc.perform(post("/expenses")
                    .content(objectMapper.writeValueAsString(expense))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expense.getAmount()))
                .andExpect(jsonPath("$.description").value(expense.getDescription()))
                .andExpect(jsonPath("$.date").value(expense.getDate().toString()));
    }

    @Test
    void shouldDeleteExpense() throws Exception {
        Expense expense = new Expense(new BigDecimal("15.66"), "Buy a new canvas", LocalDate.now());
        repository.save(expense);
        this.mockMvc.perform(delete("/expenses/{id}", expense.getId())).andDo(print()).andExpect(status().isOk());
    }
}
