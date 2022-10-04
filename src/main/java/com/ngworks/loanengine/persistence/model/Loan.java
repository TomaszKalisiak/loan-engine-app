package com.ngworks.loanengine.persistence.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private BigDecimal principal;

    @Column(name = "due_date")
    private Instant dueDate;

    public Loan(BigDecimal principal, Instant dueDate) {
        this.principal = principal;
        this.dueDate = dueDate;
    }
}
