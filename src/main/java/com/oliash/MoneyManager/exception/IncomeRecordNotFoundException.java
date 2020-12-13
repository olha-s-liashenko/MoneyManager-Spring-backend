package com.oliash.MoneyManager.exception;

public class IncomeRecordNotFoundException extends RuntimeException {

    public IncomeRecordNotFoundException(Long id) {
        super("Couldn't find Income record with ID " + id);
    }
}
