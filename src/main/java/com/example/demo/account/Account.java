package com.example.demo.account;

import com.example.demo.user.CustomUserDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
