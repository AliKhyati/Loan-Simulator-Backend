package com.loansim.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Prêt à taux fixe remboursé par mensualités constantes, hors assurance.
 *
 * Cette classe ne dépend ni de Spring ni d'aucun framework : elle contient
 * uniquement la logique de calcul, ce qui la rend facile à tester.
 *
 * @param principal         capital emprunté, en euros
 * @param annualRatePercent taux nominal annuel en pourcentage (3.5 pour 3,5 %)
 * @param durationMonths    durée du prêt en mois
 */
public record Loan(BigDecimal principal, BigDecimal annualRatePercent, int durationMonths) {

    // 34 chiffres significatifs, bien assez pour ne pas fausser l'arrondi final
    private static final MathContext PRECISION = MathContext.DECIMAL128;
    private static final BigDecimal MONTHS_TIMES_PERCENT = BigDecimal.valueOf(1200);

    public Loan {
        if (principal == null || principal.signum() <= 0) {
            throw new IllegalArgumentException("Le montant emprunté doit être strictement positif");
        }
        if (annualRatePercent == null || annualRatePercent.signum() < 0) {
            throw new IllegalArgumentException("Le taux annuel ne peut pas être négatif");
        }
        if (durationMonths <= 0) {
            throw new IllegalArgumentException("La durée doit être d'au moins 1 mois");
        }
    }

    /**
     * Mensualité hors assurance, arrondie au centime.
     *
     * Formule : M = C * t * (1 + t)^n / ((1 + t)^n - 1)
     * avec C le capital, t le taux mensuel et n le nombre de mensualités.
     */
    public BigDecimal monthlyPayment() {
        // Avec un taux nul la formule divise par zéro, on rembourse simplement le capital en parts égales
        if (annualRatePercent.signum() == 0) {
            return principal.divide(BigDecimal.valueOf(durationMonths), 2, RoundingMode.HALF_UP);
        }

        BigDecimal monthlyRate = annualRatePercent.divide(MONTHS_TIMES_PERCENT, PRECISION);
        BigDecimal growth = BigDecimal.ONE.add(monthlyRate).pow(durationMonths, PRECISION);

        BigDecimal numerator = principal.multiply(monthlyRate, PRECISION).multiply(growth, PRECISION);
        BigDecimal denominator = growth.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
