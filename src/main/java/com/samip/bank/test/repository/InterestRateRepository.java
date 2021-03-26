package com.samip.bank.test.repository;

import com.samip.bank.test.model.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate,Long> {

    @Query("Select ir from InterestRate ir where ir.terms= ?1 order by ir.interest_rate desc")
    List<InterestRate> getInterestRateByTerms(String terms);
}
