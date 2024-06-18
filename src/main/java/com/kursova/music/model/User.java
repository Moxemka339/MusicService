package com.kursova.music.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String role;


}
