package com.oliash.MoneyManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oliash.MoneyManager.model.Income;
import com.oliash.MoneyManager.repository.IncomeRepository;
import com.oliash.MoneyManager.service.IncomeService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestIncomeController {

    @Autowired
    private IncomeController controller;

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IncomeService service;

    @BeforeEach
    void prerequisites() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void shouldReturnOneIncome() throws Exception {
        Income income = new Income(new BigDecimal("34.22"), "Illustration request", LocalDate.now());
        repository.save(income);

        this.mockMvc.perform(get("/incomes/{id}", income.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(income)));
    }

    @Test
    void shouldReturnAllIncomes() throws Exception {
        List<Income> incomesToSave = new ArrayList<>();
        incomesToSave.add(new Income(
                new BigDecimal("35.80"),
                "Sold illustration",
                LocalDate.of(2020, Calendar.SEPTEMBER, 25)));
        incomesToSave.add(new Income(
                new BigDecimal("190.00"),
                "Driving fine",
                LocalDate.now()));
        incomesToSave.add(new Income(
                new BigDecimal("25.00"),
                "Coffee and cinnabon",
                LocalDate.now()));

        repository.saveAll(incomesToSave);

        this.mockMvc.perform(get("/incomes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(incomesToSave)));
    }

    @Test
    void shouldSaveIncomeAndReturn() throws Exception {
        Income income = new Income(new BigDecimal("34.22"), "Illustration request", LocalDate.now());
        income.setId(1L);
        when(service.createOne(any())).thenReturn(income);

        this.mockMvc.perform(post("/incomes")
                    .content(objectMapper.writeValueAsString(income))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(income.getAmount()))
                .andExpect(jsonPath("$.source").value(income.getSource()))
                .andExpect(jsonPath("$.date").value(income.getDate().toString()));
    }

    @Test
    void shouldDeleteIncome() throws Exception {
        Income income = new Income(new BigDecimal("34.22"), "Illustration request", LocalDate.now());
        repository.save(income);

        this.mockMvc.perform(delete("/incomes/{id}", income.getId())).andDo(print()).andExpect(status().isOk());
    }
}
