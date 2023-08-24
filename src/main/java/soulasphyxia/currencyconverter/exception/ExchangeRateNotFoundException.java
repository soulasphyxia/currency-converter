package soulasphyxia.currencyconverter.exception;

public class ExchangeRateNotFoundException extends RuntimeException{
    public ExchangeRateNotFoundException() {
        super("Обменный курс для пары не найден ");
    }
}
