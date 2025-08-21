package hotel_admin.dutkercz.com.github.model;

import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tb_room")
public class Room {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String number;
    private String singleBed;
    private String doubleBed;
    private RoomStatusEnum status;

    public Room() {
    }

    public Room(Long id, String number, String singleBed, String doubleBed, RoomStatusEnum status) {
        this.id = id;
        this.number = number;
        this.singleBed = singleBed;
        this.doubleBed = doubleBed;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSingleBed() {
        return singleBed;
    }

    public void setSingleBed(String singleBed) {
        this.singleBed = singleBed;
    }

    public String getDoubleBed() {
        return doubleBed;
    }

    public void setDoubleBed(String doubleBed) {
        this.doubleBed = doubleBed;
    }

    public RoomStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RoomStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return getId().equals(room.getId()) && getNumber().equals(room.getNumber())
                && getSingleBed().equals(room.getSingleBed())
                && getDoubleBed().equals(room.getDoubleBed()) && getStatus() == room.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getNumber().hashCode();
        result = 31 * result + getSingleBed().hashCode();
        result = 31 * result + getDoubleBed().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}
