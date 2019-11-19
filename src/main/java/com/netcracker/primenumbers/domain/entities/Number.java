package com.netcracker.primenumbers.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "Number")
public class Number {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numberID")
    private long id;

    @Column(name = "numberValue", unique = true)
    private long numberValue;

    @Column(name = "isPrime")
    private boolean isPrime;

    public Number() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(long numberValue) {
        this.numberValue = numberValue;
    }

    public boolean isPrime() {
        return isPrime;
    }

    public void setPrime(boolean prime) {
        isPrime = prime;
    }
}
