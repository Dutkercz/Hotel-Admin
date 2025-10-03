package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_extras")
public class Extras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sodaQuantity;
    private Integer waterQuantity;
    private Integer coffeeQuantity;

    @OneToOne
    @JoinColumn(name = "stay_id", nullable = false)
    private Stay stay;

    public Extras() {
    }

    public Extras(Long id, Integer sodaQuantity, Integer waterQuantity,
                  Integer coffeeQuantity, Stay stay) {
        this.id = id;
        this.sodaQuantity = sodaQuantity;
        this.waterQuantity = waterQuantity;
        this.coffeeQuantity = coffeeQuantity;
        this.stay = stay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    public Integer getSodaQuantity() {
        return sodaQuantity;
    }

    public void setSodaQuantity(Integer sodaQuantity) {
        this.sodaQuantity = sodaQuantity;
    }

    public Integer getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(Integer waterQuantity) {
        this.waterQuantity = waterQuantity;
    }

    public Integer getCoffeeQuantity() {
        return coffeeQuantity;
    }

    public void setCoffeeQuantity(Integer coffeeQuantity) {
        this.coffeeQuantity = coffeeQuantity;
    }

}
