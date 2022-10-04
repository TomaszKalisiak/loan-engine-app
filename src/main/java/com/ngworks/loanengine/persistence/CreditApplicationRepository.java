package com.ngworks.loanengine.persistence;

import com.ngworks.loanengine.persistence.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<Loan, Long> {

}
