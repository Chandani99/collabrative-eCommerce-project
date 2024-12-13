package com.api.ecommerce.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;


    public User() {
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public @NotBlank @Size(max = 20) String getName() {
        return userName;
    }

    public @NotBlank @Size(max = 50) @Email String getEmail() {
        return email;
    }

    public @NotBlank @Size(max = 120) String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Address> getAddresses() {
        return addresses;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(@NotBlank @Size(max = 20) String userName) {
        this.userName = userName;
    }

    public void setEmail(@NotBlank @Size(max = 50) @Email String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank @Size(max = 120) String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", addresses=" + addresses +
                '}';
    }
}
