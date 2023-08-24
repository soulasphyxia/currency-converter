package soulasphyxia.currencyconverter.exception;

public class CurrencyAlreadyExists  extends RuntimeException{
    public CurrencyAlreadyExists() {
        super("Валюта с таким кодом уже существует");
    }
}
