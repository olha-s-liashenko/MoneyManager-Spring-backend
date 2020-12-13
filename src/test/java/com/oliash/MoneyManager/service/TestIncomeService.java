package com.oliash.MoneyManager.service;

import com.oliash.MoneyManager.exception.IncomeRecordNotFoundException;
import com.oliash.MoneyManager.model.Income;
import com.oliash.MoneyManager.repository.IncomeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestIncomeService {

    @Autowired
    private IncomeRepository repository;

    private IncomeService service;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @BeforeEach
    void prerequisites() {
        repository.deleteAll();
        service = new IncomeService(repository);
    }

    @Test
    void testGetEmptyIncomes() {
        assertTrue(repository.findAll().isEmpty());
        Long id = 1L;
        assertThrows(IncomeRecordNotFoundException.class, () -> service.getOne(id));
    }

    @Test
    void testGetOneIncome() {
        Income testIncome = new Income(new BigDecimal("35.80"), "Sold illustration", LocalDate.of(2020, Calendar.SEPTEMBER, 25));
        repository.save(testIncome);
        assertEquals(testIncome, service.getOne(testIncome.getId()));
    }

    @Test
    void testGetAllIncomes() {
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

        List<Income> savedIncomes = service.getAll();
        LOGGER.info(savedIncomes.toString());
        assertEquals(incomesToSave, savedIncomes);
        assertEquals(incomesToSave.size(), savedIncomes.size());
    }

    @Test
    void testCreateIncome() {
        Income newIncome = new Income(new BigDecimal("190"), "Driving fine", LocalDate.now());
        assertEquals(newIncome, service.createOne(newIncome));
    }

    @Test
    void testCreateManyIncomes() {
        List<Income> incomesToSave = new ArrayList<>();
        incomesToSave.add(new Income(
                new BigDecimal("35.80"),
                "Sold illustration",
                LocalDate.of(2020, Calendar.SEPTEMBER, 25)));
        incomesToSave.add(new Income(
                new BigDecimal("190"),
                "Driving fine",
                LocalDate.now()));

        assertEquals(incomesToSave, service.createMany(incomesToSave));
    }

    @Test
    void testDeleteIncome() {
        Income testIncome = new Income(new BigDecimal("35.80"), "Sold illustration", LocalDate.of(2020, Calendar.SEPTEMBER, 25));
        repository.save(testIncome);
        service.delete(testIncome.getId());
        assertFalse(repository.existsById(testIncome.getId()));
    }
}
