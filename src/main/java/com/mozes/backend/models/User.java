package com.mozes.backend.models;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private boolean active;

    @NonNull
    @Column
    private String address;
    @Column
    private boolean created;
    @Column
    private Date created_at;
    @Column
    private boolean deleted;
    @Column
    private Date deleted_at;
    @Column
    private String delete_flag;

    @NonNull
    @Column
    private String email;
    @Column
    private String email_token;
    @Column
    private Date last_login;

    @NonNull
    @Column
    private String name;
    @Column
    private boolean next_login_change_pwd;

    @NonNull
    @Column(length = 255)
    private String password;
    @Column
    private boolean password_expired;
    @Column
    private String phone;
    @Column
    private int settlement_id;
    @Column
    private String temp_password;
    @Column
    private boolean temp_password_expired;
    @Column
    private boolean updated;
    @Column
    private Date updated_at;
    @Column
    private String user_type;

    @NonNull
    @Column
    private String username;
    @Column
    private int settlements_by_settlement_id;
    @Column
    private int user_by_created_id;
    @Column
    private int user_by_updated_id;
    @Column
    private int user_by_deleted_id;

    public User() {

    }
}
