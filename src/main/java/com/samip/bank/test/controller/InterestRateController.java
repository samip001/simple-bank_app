package com.samip.bank.test.controller;

import com.samip.bank.test.dao.InterestRateDAO;
import com.samip.bank.test.model.InterestRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class InterestRateController {

    private InterestRateDAO interestRateDAO;

    @Autowired
    private InterestRateController(InterestRateDAO interestRateDAO){
        this.interestRateDAO = interestRateDAO;
    }

    @GetMapping("/bankinterest")
    public String getBankInterestPage(Model model){
        model.addAttribute("titlename","Add New Bank Interest");
        model.addAttribute("interestRate",new InterestRate());
        return "add_bank_interest_rate";
    }

    @PostMapping("/addbankinterest")
    public String addBankInterestRate(InterestRate interestRate,Model model){
        InterestRate newInterestRate = interestRateDAO.addInterestRate(interestRate);
        model.addAttribute("titlename","All Banks List");
        if(newInterestRate != null){
            model.addAttribute("message","Added Successfully");
            model.addAttribute("interestRate",new InterestRate());
            return "add_bank_interest_rate";
        }

        model.addAttribute("interestRates",interestRateDAO.getAllInterestRate());
        return "bank_list";
    }

    @GetMapping("/bank/{bankid}")
    public String getBankDetails(@PathVariable("bankid")Long id,Model model){
        Optional<InterestRate> interestRate = interestRateDAO.getInterestRate(id);
        if(interestRate.isPresent()){
            model.addAttribute("titlename",interestRate.get().getBankName()+" Details");
            model.addAttribute("found",true);
            model.addAttribute("interestRate",interestRate.get());
        }else{
            model.addAttribute("titlename","No such Bank found");
            model.addAttribute("found",false);
        }

        return "bank_details";
    }

    @GetMapping("/banks")
    public String getAllBanksList(Model model){
        List<InterestRate> interestRates = interestRateDAO.getAllInterestRate();
        chekBankEmptyOrNot(model, interestRates);

        return "bank_list";
    }

    private void chekBankEmptyOrNot(Model model, List<InterestRate> interestRates) {
        if(interestRates.size() < 1){
            model.addAttribute("titlename","No Result Found");
            model.addAttribute("found",false);
        }else{
            model.addAttribute("titlename","All Banks List");
            model.addAttribute("interestRates",interestRates);
            model.addAttribute("found",true);
        }
    }

    @GetMapping("/search")
    public String searchBankInterestRate(@RequestParam("interestrate")String interestRate,Model model){
        List<InterestRate> interestRates =interestRateDAO.getInterestRateByTerms(interestRate);
        model.addAttribute("titlename","Search Result for Terms: "+interestRate+" Years");

        chekBankEmptyOrNot(model, interestRates);
        return "search_result";
    }


}
