package com.oliash.MoneyManager.exception;

public class ExpenseRecordNotFoundException extends RuntimeException {
    public ExpenseRecordNotFoundException(Long id) {
        super("Couldn't find expense record with ID " + id);
    }
}
