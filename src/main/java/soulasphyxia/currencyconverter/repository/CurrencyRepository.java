package soulasphyxia.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soulasphyxia.currencyconverter.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    Currency findCurrencyByCode(String code);

    boolean existsCurrencyByCode(String code);

}
