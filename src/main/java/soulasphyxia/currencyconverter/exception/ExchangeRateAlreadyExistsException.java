package soulasphyxia.currencyconverter.exception;

public class ExchangeRateAlreadyExistsException extends RuntimeException{
    public ExchangeRateAlreadyExistsException() {
        super("Валюнтая пара с таким кодом уже существует");
    }
}
