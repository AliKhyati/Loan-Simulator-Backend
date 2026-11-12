package com.loansim.application;

import com.loansim.domain.Loan;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    public SimulationResult calculate(SimulationRequest request) {
        Loan loan = new Loan(request.principal(), request.annualRatePercent(), request.durationMonths());
        return new SimulationResult(loan.monthlyPayment());
    }
}
