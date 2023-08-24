package soulasphyxia.currencyconverter.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exchangerates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="basecurrencyid",referencedColumnName = "currency_id")
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name="targetcurrencyid",referencedColumnName = "currency_id")
    private Currency targetCurrecy;


    @Column(name = "rate")
    private Double rate;

}
