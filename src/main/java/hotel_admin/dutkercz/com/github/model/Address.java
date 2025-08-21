package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {

    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String country;
    private String cep;

    public Address() {
    }

    public Address(String street, String number, String district, String city, String state, String country, String cep) {
        this.street = street;
        this.number = number;
        this.district = district;
        this.city = city;
        this.state = state;
        this.country = country;
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;
        return Objects.equals(getStreet(), address.getStreet())
                && Objects.equals(getNumber(), address.getNumber())
                && Objects.equals(getDistrict(), address.getDistrict())
                && Objects.equals(getCity(), address.getCity())
                && Objects.equals(getState(), address.getState())
                && Objects.equals(getCountry(), address.getCountry())
                && Objects.equals(getCep(), address.getCep());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getStreet());
        result = 31 * result + Objects.hashCode(getNumber());
        result = 31 * result + Objects.hashCode(getDistrict());
        result = 31 * result + Objects.hashCode(getCity());
        result = 31 * result + Objects.hashCode(getState());
        result = 31 * result + Objects.hashCode(getCountry());
        result = 31 * result + Objects.hashCode(getCep());
        return result;
    }
}
