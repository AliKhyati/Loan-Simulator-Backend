package com.loansim.application;

import java.math.BigDecimal;

public record SimulationRequest(BigDecimal principal, BigDecimal annualRatePercent, int durationMonths) {
}
