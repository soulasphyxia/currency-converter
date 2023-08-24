package soulasphyxia.currencyconverter.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soulasphyxia.currencyconverter.exception.CurrencyAlreadyExists;
import soulasphyxia.currencyconverter.exception.ExchangeRateAlreadyExistsException;
import soulasphyxia.currencyconverter.exception.ExchangeRateNotFoundException;
import soulasphyxia.currencyconverter.model.ExchangeRate;
import soulasphyxia.currencyconverter.model.ExchangeResult;
import soulasphyxia.currencyconverter.repository.ExchangeRateRepository;
import soulasphyxia.currencyconverter.service.ExchangeRateService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/exchangeRates")
    public List<ExchangeRate> doGetAllExchangeRates(){
        return exchangeRateService.allExchangeRates();
    }

    @GetMapping("/exchangeRate/{pairCode}")
    public ExchangeRate doGetExchangeRateByPairCode(@PathVariable String pairCode){
        return exchangeRateService.getExchangeRateByPairCode(pairCode);
    }

    @PostMapping("/exchangeRates")
    public ExchangeRate doPostExchangeRate(@RequestParam Map<String, String> saveRequest) {
        return exchangeRateService.saveExchangeRate(saveRequest);
    }

    @PatchMapping("/exchangeRate/{pairCode}/")
    public ExchangeRate doPatchExchangeRateByPairCode(@PathVariable String pairCode, @RequestParam Map<String,String> patchRequest) {
        System.out.println(patchRequest.get("rate"));
        return exchangeRateService.patchExchangeRate(pairCode,patchRequest);
    }

    @GetMapping("/exchange/")
    public ResponseEntity<?> doGetExchangeResult(@RequestParam String from, @RequestParam String to, @RequestParam String amount) {
        return exchangeRateService.exchange(from,to, Double.valueOf(amount));
    }

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public ResponseEntity<?> handle(ExchangeRateNotFoundException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExchangeRateAlreadyExistsException.class)
    public ResponseEntity<?> handle(ExchangeRateAlreadyExistsException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.CONFLICT);
    }

}
