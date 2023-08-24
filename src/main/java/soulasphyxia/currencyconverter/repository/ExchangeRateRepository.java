package soulasphyxia.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soulasphyxia.currencyconverter.model.Currency;
import soulasphyxia.currencyconverter.model.ExchangeRate;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {
    ExchangeRate findExchangeRateByBaseCurrencyAndTargetCurrecy(Currency baseCurrency, Currency targetCurrency);

    boolean existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode(String code1, String code2);

    List<ExchangeRate> findAllByOrderByIdAsc();
}
