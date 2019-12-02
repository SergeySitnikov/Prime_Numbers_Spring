package com.netcracker.primenumbers.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login", length = 15, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},  fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

//    @ManyToMany(mappedBy = "users")
//    private Set<Number> numbers;

    @OneToMany(mappedBy = "firstUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},  fetch = FetchType.EAGER)
    private Set<Number> firstInNumbers;

    public User() {
    }

    public Set<Number> getFirstInNumbers() {
        return firstInNumbers;
    }

    public void setFirstInNumbers(Set<Number> firstInNumbers) {
        this.firstInNumbers = firstInNumbers;
    }

//    public Set<Number> getNumbers() {
//        return numbers;
//    }
//
//    public void setNumbers(Set<Number> numbers) {
//        this.numbers = numbers;
//    }

    public Boolean hasRole(String role) {
        return this.roles.stream().map(x->x.getRole()).anyMatch(role::equals);
    }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
