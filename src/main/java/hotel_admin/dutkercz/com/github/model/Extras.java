package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_extras")
public class Extras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal sodaExtraPrice;
    private BigDecimal waterExtraPrice;
    private BigDecimal coffeeExtraPrice;
    private BigDecimal companionExtraPrice;

    @OneToOne
    @JoinColumn(name = "stay_id", nullable = false)
    private Stay stay;

    public Extras() {
    }

    public Extras(Long id, BigDecimal sodaExtraPrice, BigDecimal waterExtraPrice,
                  BigDecimal coffeeExtraPrice, BigDecimal companionExtraPrice, Stay stay) {
        this.id = id;
        this.sodaExtraPrice = sodaExtraPrice;
        this.waterExtraPrice = waterExtraPrice;
        this.coffeeExtraPrice = coffeeExtraPrice;
        this.companionExtraPrice = companionExtraPrice;
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

    public BigDecimal getSodaExtraPrice() {
        return sodaExtraPrice;
    }

    public void setSodaExtraPrice(BigDecimal sodaExtraPrice) {
        this.sodaExtraPrice = sodaExtraPrice;
    }

    public BigDecimal getWaterExtraPrice() {
        return waterExtraPrice;
    }

    public void setWaterExtraPrice(BigDecimal waterExtraPrice) {
        this.waterExtraPrice = waterExtraPrice;
    }

    public BigDecimal getCoffeeExtraPrice() {
        return coffeeExtraPrice;
    }

    public void setCoffeeExtraPrice(BigDecimal coffeeExtraPrice) {
        this.coffeeExtraPrice = coffeeExtraPrice;
    }

    public BigDecimal getCompanionExtraPrice() {
        return companionExtraPrice;
    }

    public void setCompanionExtraPrice(BigDecimal companionExtraPrice) {
        this.companionExtraPrice = companionExtraPrice;
    }

}
