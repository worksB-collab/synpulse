package com.example.demo.transaction;

import com.example.demo.user.CustomUserDetails;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;
  private BigDecimal amount;
  private String currency;
  private String accountIban;
  private LocalDate valueDate;

  @ManyToOne(cascade = CascadeType.REMOVE)
  private CustomUserDetails user;
}