package soulasphyxia.currencyconverter.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="currencies")
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="currency_id")
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="full_name")
    private String fullName;

    @Column(name="sign")
    private String sign;

}
