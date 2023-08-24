package soulasphyxia.currencyconverter.model;

import lombok.Data;

@Data
public class ExchangeResult {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private Double rate;
    private Double amount;
    private Double convertedAmount;

    public ExchangeResult(Currency baseCurrency, Currency targetCurrency,
                          Double rate, Double amount) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.amount = amount;
        this.convertedAmount = rate*amount;
    }
}
