package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String country;
    private String cep;
}
