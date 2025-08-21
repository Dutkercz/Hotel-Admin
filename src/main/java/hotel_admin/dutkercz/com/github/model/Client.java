package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tb_client")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fistName;
    private String lastName;
    private String cpf;
    @Embedded
    private Address address;

    public Client() {
    }

    public Client(Long id, String fistName, String lastName, String cpf, Address address) {
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        return Objects.equals(getId(), client.getId()) && Objects.equals(getFistName(), client.getFistName()) && Objects.equals(getLastName(), client.getLastName()) && Objects.equals(getCpf(), client.getCpf()) && Objects.equals(getAddress(), client.getAddress());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getFistName());
        result = 31 * result + Objects.hashCode(getLastName());
        result = 31 * result + Objects.hashCode(getCpf());
        result = 31 * result + Objects.hashCode(getAddress());
        return result;
    }
}
