package com.samip.bank.test.dao.imp;

import com.samip.bank.test.dao.InterestRateDAO;
import com.samip.bank.test.model.InterestRate;
import com.samip.bank.test.repository.InterestRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestRateDAOImpl  implements InterestRateDAO {

    private InterestRateRepository interestRateRepository;

    @Autowired
    public InterestRateDAOImpl(InterestRateRepository interestRateRepository){
        this.interestRateRepository = interestRateRepository;
    }

    @Override
    public InterestRate addInterestRate(InterestRate interestRate) {
        return interestRateRepository.save(interestRate);
    }

    @Override
    public Optional<InterestRate> getInterestRate(Long id) {
        return interestRateRepository.findById(id);

    }

    @Override
    public List<InterestRate> getAllInterestRate() {
        return interestRateRepository.findAll();
    }

    @Override
    public List<InterestRate> getInterestRateByTerms(String terms) {
        List<InterestRate> interestRateByTerms = interestRateRepository.getInterestRateByTerms(terms);
        return interestRateByTerms;
    }
}
