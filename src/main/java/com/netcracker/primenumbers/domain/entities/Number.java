package com.netcracker.primenumbers.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Number")
public class Number {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number_id")
    private long id;

    @Column(name = "numberValue", unique = true)
    private long numberValue;

    @Column(name = "isPrime")
    private boolean isPrime;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "number_user", inverseJoinColumns = @JoinColumn(name = "user_id"), joinColumns = @JoinColumn(name = "number_id"))
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User firstUser;

    public Number() {
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
