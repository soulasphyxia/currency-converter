package soulasphyxia.currencyconverter.exception;

public class FieldIsNullException extends RuntimeException{
    public FieldIsNullException(){
        super("Отсутствует нужное поле формы");
    }
}
