# Loan Simulator

API REST de simulation de prêt immobilier, écrite en Java avec Spring Boot.


## Stack

- Java 21
- Spring Boot 4
- Maven
- JUnit pour les tests

## Lancer le projet

```bash
mvn spring-boot:run
```

L'API écoute sur `http://localhost:8080`.

## Lancer les tests

```bash
mvn test
```

## Exemple

`POST /api/simulations/calculate`

```json
{
  "principal": 200000,
  "annualRatePercent": 3.5,
  "durationMonths": 240
}
```

Réponse :

```json
{
  "monthlyPayment": 1159.92
}
```

## Architecture

```
com.loansim
├── domain         logique de calcul, en Java pur (aucune dépendance à Spring)
├── application    services et objets d'échange (requête / résultat)
└── api            contrôleurs REST
```

