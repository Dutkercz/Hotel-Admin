package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

//entidade que representa as pessoas adicionais no apto. Podendo ser mais de uma.
@Entity
@Table(name = "tb_stay_guest")
public class StayGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne()
    private Stay stay;

    public StayGuest() {
    }

    public StayGuest(Long id, String name, Stay stay) {
        this.id = id;
        this.name = name;
        this.stay = stay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        StayGuest stayGuest = (StayGuest) o;
        return getId().equals(stayGuest.getId())
                && Objects.equals(getName(), stayGuest.getName())
                && Objects.equals(getStay(), stayGuest.getStay());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + Objects.hashCode(getName());
        result = 31 * result + Objects.hashCode(getStay());
        return result;
    }
}
