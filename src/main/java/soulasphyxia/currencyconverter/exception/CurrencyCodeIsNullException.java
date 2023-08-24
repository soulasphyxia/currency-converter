package soulasphyxia.currencyconverter.exception;

public class CurrencyCodeIsNullException extends RuntimeException{
    public CurrencyCodeIsNullException() {
        super("Код валюты отсутствует в адресе");
    }
}
