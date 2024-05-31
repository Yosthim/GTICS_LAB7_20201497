package com.example.lab7_20201497.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int userId;
    private String name;
    private String type;
    @ManyToOne
    @JoinColumn(name = "authorizedresource",nullable = false)
    private Resources authorizedResource;
    private boolean active;
}
