package com.oliash.MoneyManager.service;

import com.oliash.MoneyManager.exception.IncomeRecordNotFoundException;
import com.oliash.MoneyManager.model.Income;
import com.oliash.MoneyManager.repository.IncomeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public Income getOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new IncomeRecordNotFoundException(id));
    }

    public List<Income> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public Income createOne(Income income) {
        return repository.save(income);
    }

    public List<Income> createMany(List<Income> incomesToSave) {
        return repository.saveAll(incomesToSave);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
