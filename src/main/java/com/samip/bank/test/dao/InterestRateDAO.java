package com.samip.bank.test.dao;

import com.samip.bank.test.model.InterestRate;

import java.util.List;
import java.util.Optional;

public interface InterestRateDAO {

    InterestRate addInterestRate(InterestRate interestRate);
    Optional<InterestRate> getInterestRate(Long id);
    List<InterestRate> getAllInterestRate();
    List<InterestRate> getInterestRateByTerms(String terms);

}
