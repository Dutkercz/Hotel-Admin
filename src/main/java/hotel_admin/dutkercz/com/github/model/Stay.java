package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "stay", orphanRemoval = true)
    private List<StayGuest> guests = new ArrayList<>();

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Stay() {
    }

    public Stay(Long id, Client client, Room room, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.id = id;
        this.client = client;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Stay stay = (Stay) o;
        return getId().equals(stay.getId())
                && Objects.equals(getClient(), stay.getClient())
                && Objects.equals(getRoom(), stay.getRoom())
                && Objects.equals(getCheckIn(), stay.getCheckIn())
                && Objects.equals(getCheckOut(), stay.getCheckOut());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + Objects.hashCode(getClient());
        result = 31 * result + Objects.hashCode(getRoom());
        result = 31 * result + Objects.hashCode(getCheckIn());
        result = 31 * result + Objects.hashCode(getCheckOut());
        return result;
    }
}
