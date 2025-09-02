package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

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

}
