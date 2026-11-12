package com.loansim.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Les valeurs attendues ont été calculées en dehors du code (pas avec Loan !).
 * À recouper avec un tableur : fonction VPM en français, PMT en anglais.
 *
 * Attention : assertEquals sur des BigDecimal compare aussi l'échelle,
 * donc 1000.0 et 1000.00 sont considérés comme différents.
 */
class LoanTest {

    @Test
    void monthlyPayment_pretClassique() {
        Loan loan = new Loan(new BigDecimal("200000"), new BigDecimal("3.5"), 240);

        assertEquals(new BigDecimal("1159.92"), loan.monthlyPayment());
    }

    @Test
    void monthlyPayment_tauxNul() {
        Loan loan = new Loan(new BigDecimal("12000"), BigDecimal.ZERO, 12);

        assertEquals(new BigDecimal("1000.00"), loan.monthlyPayment());
    }

    @ParameterizedTest
    @CsvSource({
            "100000, 2,    120, 920.13",
            "150000, 4,    300, 791.76",
            "250000, 3.85, 300, 1298.97"
    })
    void monthlyPayment_plusieursCas(String principal, String rate, int months, String expected) {
        Loan loan = new Loan(new BigDecimal(principal), new BigDecimal(rate), months);

        assertEquals(new BigDecimal(expected), loan.monthlyPayment());
    }

    @Test
    void refuseUnMontantNul() {
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(BigDecimal.ZERO, new BigDecimal("3"), 240));
    }

    @Test
    void refuseUnTauxNegatif() {
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(new BigDecimal("100000"), new BigDecimal("-1"), 240));
    }

    @Test
    void refuseUneDureeNulle() {
        assertThrows(IllegalArgumentException.class,
                () -> new Loan(new BigDecimal("100000"), new BigDecimal("3"), 0));
    }
}
