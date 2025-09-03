package hotel_admin.dutkercz.com.github.model;

import hotel_admin.dutkercz.com.github.model.enums.ClientGenderEnum;
import hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tb_client")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String cpf;
    private String phone;

    @Enumerated(EnumType.STRING)
    private ClientGenderEnum gender;

    @Enumerated(EnumType.STRING)
    private ClientStatusEnum status;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "client")
    private List<Stay> stays = new ArrayList<>();

    public Client() {
    }

    public Client(Long id, String firstName, String lastName, String cpf, String phone, ClientGenderEnum gender, Address address, ClientStatusEnum status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.phone = phone;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fistName) {
        this.firstName = fistName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void addStay(Stay stay) {
        stays.add(stay);
        stay.setClient(this);
    }

    public void removeStay(Stay stay) {
        stays.remove(stay);
        stay.setClient(null);
    }

    public List<Stay> getStays() {
        return stays;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;
        return getId().equals(client.getId())
                && Objects.equals(getFirstName(), client.getFirstName())
                && Objects.equals(getLastName(), client.getLastName())
                && Objects.equals(getCpf(), client.getCpf())
                && Objects.equals(getPhone(), client.getPhone())
                && getGender() == client.getGender()
                && getStatus() == client.getStatus()
                && Objects.equals(getAddress(), client.getAddress());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + Objects.hashCode(getFirstName());
        result = 31 * result + Objects.hashCode(getLastName());
        result = 31 * result + Objects.hashCode(getCpf());
        result = 31 * result + Objects.hashCode(getPhone());
        result = 31 * result + Objects.hashCode(getGender());
        result = 31 * result + Objects.hashCode(getStatus());
        result = 31 * result + Objects.hashCode(getAddress());
        return result;
    }

}
