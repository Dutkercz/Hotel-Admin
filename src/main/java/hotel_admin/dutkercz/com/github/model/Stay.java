package hotel_admin.dutkercz.com.github.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_stay")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "stay", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StayGuest> guests = new ArrayList<>();

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal stayPrice;
    private Integer stayAmount;

    @Enumerated(EnumType.STRING)
    private StayStatusEnum status;

    public Stay() {
    }

    public Stay(Long id, Client client, Room room, LocalDateTime checkIn, LocalDateTime checkOut, BigDecimal stayPrice, Integer stayAmount, StayStatusEnum status) {
        this.id = id;
        this.client = client;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.stayPrice = stayPrice;
        this.stayAmount = stayAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<StayGuest> getGuests() {
        return guests;
    }

    public void setGuests(List<StayGuest> guests) {
        this.guests = guests;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public void addGuest(StayGuest guest){
        guests.add(guest);
        guest.setStay(this);
    }

    public void removeGuest(StayGuest guest){
        guests.remove(guest);
        guest.setStay(null);
    }

    public BigDecimal getStayPrice() {
        return stayPrice;
    }

    public void setStayPrice(BigDecimal stayPrice) {
        this.stayPrice = stayPrice;
    }

    public Integer getStayAmount() {
        return stayAmount;
    }

    public void setStayAmount(Integer stayAmount) {
        this.stayAmount = stayAmount;
    }

    public StayStatusEnum getStatus() {
        return status;
    }

    public void setStatus(StayStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Stay stay = (Stay) o;
        return Objects.equals(getId(), stay.getId())
                && Objects.equals(getClient(), stay.getClient())
                && Objects.equals(getRoom(), stay.getRoom())
                && Objects.equals(getGuests(), stay.getGuests())
                && Objects.equals(getCheckIn(), stay.getCheckIn())
                && Objects.equals(getCheckOut(), stay.getCheckOut())
                && Objects.equals(getStayPrice(), stay.getStayPrice())
                && Objects.equals(getStayAmount(), stay.getStayAmount())
                && getStatus() == stay.getStatus();
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getClient());
        result = 31 * result + Objects.hashCode(getRoom());
        result = 31 * result + Objects.hashCode(getGuests());
        result = 31 * result + Objects.hashCode(getCheckIn());
        result = 31 * result + Objects.hashCode(getCheckOut());
        result = 31 * result + Objects.hashCode(getStayPrice());
        result = 31 * result + Objects.hashCode(getStayAmount());
        result = 31 * result + Objects.hashCode(getStatus());
        return result;
    }
}
