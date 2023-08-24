package soulasphyxia.currencyconverter.service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soulasphyxia.currencyconverter.exception.CurrencyCodeIsNullException;
import soulasphyxia.currencyconverter.exception.ExchangeRateAlreadyExistsException;
import soulasphyxia.currencyconverter.exception.ExchangeRateNotFoundException;
import soulasphyxia.currencyconverter.model.Currency;
import soulasphyxia.currencyconverter.model.ExchangeRate;
import soulasphyxia.currencyconverter.model.ExchangeResult;
import soulasphyxia.currencyconverter.repository.CurrencyRepository;
import soulasphyxia.currencyconverter.repository.ExchangeRateRepository;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;


    public List<ExchangeRate> allExchangeRates() {
        return exchangeRateRepository.findAllByOrderByIdAsc();
    }

    public ExchangeRate getExchangeRateByPairCode(String pairCode) {
        Object[] currencyPair = getCurrencyPair(pairCode);
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByBaseCurrencyAndTargetCurrecy((Currency) currencyPair[0], (Currency) currencyPair[1]);
        if(exchangeRate == null) {
            throw new ExchangeRateNotFoundException();
        }else {
            return exchangeRate;
        }
    }


    public ExchangeRate saveExchangeRate(Map<String,String> saveRequest) {
        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setBaseCurrency(currencyRepository.findCurrencyByCode(saveRequest.get("baseCurrencyCode")));
        exchangeRate.setTargetCurrecy(currencyRepository.findCurrencyByCode(saveRequest.get("targetCurrencyCode")));
        exchangeRate.setRate(Double.parseDouble(saveRequest.get("rate")));
        if(exchangeRateRepository.existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode(exchangeRate.getBaseCurrency().getCode(), exchangeRate.getTargetCurrecy().getCode())){
            throw new ExchangeRateAlreadyExistsException();
        }else {
            exchangeRateRepository.save(exchangeRate);
            return exchangeRate;
        }
    }


    public ExchangeRate patchExchangeRate(String pairCode,Map<String,String> patchRequest) {
        ExchangeRate exchangeRateToPatch = getExchangeRateByPairCode(pairCode);
        exchangeRateToPatch.setRate(Double.parseDouble(patchRequest.get("rate")));
        exchangeRateRepository.save(exchangeRateToPatch);
        return exchangeRateToPatch;
    }


    public ResponseEntity<?> exchange(String from, String to, Double amount) {
        ExchangeResult result = getExchangeResult(from, to, amount);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    private ExchangeResult getExchangeResult(String baseCurrencyCode, String targetCurrencyCode, Double amount) {
        if(exchangeRateRepository.existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode(baseCurrencyCode, targetCurrencyCode)) {
            return new ExchangeResult(currencyRepository.findCurrencyByCode(baseCurrencyCode),
                                      currencyRepository.findCurrencyByCode(targetCurrencyCode),
                                      getExchangeRateByPairCode(baseCurrencyCode+targetCurrencyCode).getRate(),
                                      amount
                    );
        }else if(
                !exchangeRateRepository.existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode(baseCurrencyCode,targetCurrencyCode)
                && exchangeRateRepository.existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode(targetCurrencyCode, baseCurrencyCode)) {
            return new ExchangeResult(currencyRepository.findCurrencyByCode(baseCurrencyCode),
                               currencyRepository.findCurrencyByCode(targetCurrencyCode),
                               1/getExchangeRateByPairCode(baseCurrencyCode + targetCurrencyCode).getRate(),
                                amount);
        } else if (exchangeRateRepository.existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode("USD", baseCurrencyCode)
                && exchangeRateRepository.existsExchangeRateByBaseCurrencyCodeAndTargetCurrecyCode("USD", targetCurrencyCode)
        ) {
            Double baseRate = getExchangeRateByPairCode("USD" + baseCurrencyCode).getRate();
            Double targetRate = getExchangeRateByPairCode("USD" + targetCurrencyCode).getRate();
            Double rate = 1/baseRate*targetRate;
            return new ExchangeResult(currencyRepository.findCurrencyByCode(baseCurrencyCode),
                    currencyRepository.findCurrencyByCode(targetCurrencyCode),rate,amount);
        }
        return null;
    }


    private Object[] getCurrencyPair(String pairCode) {
        if(pairCode.length() != 6) {
            throw new CurrencyCodeIsNullException();
        }else {
            String baseCurrencyCode = pairCode.substring(0,3);
            String targetCurrencyCode = pairCode.substring(3);
            Currency baseCurrency = currencyRepository.findCurrencyByCode(baseCurrencyCode);
            Currency targetCurrency = currencyRepository.findCurrencyByCode(targetCurrencyCode);
            return  new Object[]{baseCurrency,targetCurrency};
        }
    }

}
