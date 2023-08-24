package soulasphyxia.currencyconverter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import soulasphyxia.currencyconverter.exception.CurrencyAlreadyExists;
import soulasphyxia.currencyconverter.exception.CurrencyCodeIsNullException;
import soulasphyxia.currencyconverter.exception.CurrencyNotFoundException;
import soulasphyxia.currencyconverter.exception.FieldIsNullException;
import soulasphyxia.currencyconverter.model.Currency;
import soulasphyxia.currencyconverter.repository.CurrencyRepository;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getCurrencyByCode(String code) {
        Currency  currency = currencyRepository.findCurrencyByCode(code);
        if(currency == null) {
            throw new CurrencyNotFoundException(code);
        }else{
            return currency;
        }
    }

    public Currency saveCurrency(Map<String,String> saveRequest) {
        Currency currency = new Currency();
        String code = saveRequest.get("code");
        String sign = saveRequest.get("sign");
        String fullName = saveRequest.get("fullName");
        if(code.isEmpty() || sign.isEmpty() || fullName.isEmpty()) {
            throw new FieldIsNullException();
        }else {
            currency.setCode(code);
            currency.setSign(sign);
            currency.setFullName(fullName);
            if(currencyRepository.existsCurrencyByCode(code)) {
                throw new CurrencyAlreadyExists();
            }else {
                currencyRepository.save(currency);
                return currency;
            }
        }
    }

}
