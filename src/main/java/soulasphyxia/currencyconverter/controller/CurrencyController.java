package soulasphyxia.currencyconverter.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soulasphyxia.currencyconverter.exception.CurrencyAlreadyExists;
import soulasphyxia.currencyconverter.exception.CurrencyCodeIsNullException;
import soulasphyxia.currencyconverter.exception.CurrencyNotFoundException;
import soulasphyxia.currencyconverter.exception.FieldIsNullException;
import soulasphyxia.currencyconverter.model.Currency;
import soulasphyxia.currencyconverter.service.CurrencyService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CurrencyController {
    private CurrencyService currencyService;

    @GetMapping("/currencies")
    public List<Currency> doGetAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/currency/{code}")
    public Currency doGetCurrencyByCode(@PathVariable String code) {
        if(code == null) {
            throw new CurrencyCodeIsNullException();
        }else {
            return currencyService.getCurrencyByCode(code);
        }
    }

    @PostMapping("/currencies")
    public Currency doPostCurrency(@RequestParam Map<String,String> currency) {
        return currencyService.saveCurrency(currency);
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<?> handle(CurrencyNotFoundException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrencyCodeIsNullException.class)
    public ResponseEntity<?> handle(CurrencyCodeIsNullException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldIsNullException.class)
    public ResponseEntity<?> handle(FieldIsNullException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CurrencyAlreadyExists.class)
    public ResponseEntity<?> handle(CurrencyAlreadyExists e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.CONFLICT);
    }

}
