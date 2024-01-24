package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private CustomUserDetails user;

    public Account(final CustomUserDetails user) {
        this.user = user;
    }

}
