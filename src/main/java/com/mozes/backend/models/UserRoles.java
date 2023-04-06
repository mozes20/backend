package com.mozes.backend.models;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
@RequiredArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NonNull
    @Column(name = "roles")
    private String roles;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    public UserRoles() {

    }
}
