package com.oliash.MoneyManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Income {

    @Id @GeneratedValue
    private Long id;
    private BigDecimal amount;
    private String source;
    private LocalDate date;

    public Income() {
    }

    public Income(BigDecimal amount, String source, LocalDate date) {
        this.amount = amount;
        this.source = source;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return id.equals(income.id) &&
                amount.equals(income.amount) &&
                source.equals(income.source) &&
                date.equals(income.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, source, date);
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", amount=" + amount +
                ", source='" + source + '\'' +
                ", date=" + date +
                '}';
    }
}
