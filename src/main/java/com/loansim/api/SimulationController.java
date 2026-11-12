package com.loansim.api;

import com.loansim.application.SimulationRequest;
import com.loansim.application.SimulationResult;
import com.loansim.application.SimulationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simulations")
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/calculate")
    public SimulationResult calculate(@RequestBody SimulationRequest request) {
        return simulationService.calculate(request);
    }
}
