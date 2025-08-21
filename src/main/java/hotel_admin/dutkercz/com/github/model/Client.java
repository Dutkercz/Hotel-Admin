package hotel_admin.dutkercz.com.github.model;

import hotel_admin.dutkercz.com.github.model.enums.ClientGenderEnum;
import hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum;
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

    @Enumerated(EnumType.STRING)
    private ClientGenderEnum gender;

    @Enumerated(EnumType.STRING)
    private ClientStatusEnum status;

    @Embedded
    private Address address;

    public Client() {
    }

    public Client(Long id, String fistName, String lastName, String cpf, ClientGenderEnum gender, Address address, ClientStatusEnum status) {
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.gender = gender;
        this.address = address;
        this.status = status;
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

    public ClientGenderEnum getGender() {
        return gender;
    }

    public void setGender(ClientGenderEnum gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ClientStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ClientStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        return getId().equals(client.getId())
                && Objects.equals(getFistName(), client.getFistName())
                && Objects.equals(getLastName(), client.getLastName())
                && Objects.equals(getCpf(), client.getCpf())
                && getGender() == client.getGender()
                && getStatus() == client.getStatus()
                && Objects.equals(getAddress(), client.getAddress());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + Objects.hashCode(getFistName());
        result = 31 * result + Objects.hashCode(getLastName());
        result = 31 * result + Objects.hashCode(getCpf());
        result = 31 * result + Objects.hashCode(getGender());
        result = 31 * result + Objects.hashCode(getStatus());
        result = 31 * result + Objects.hashCode(getAddress());
        return result;
    }
}
