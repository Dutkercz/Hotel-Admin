package hotel_admin.dutkercz.com.github.model;

import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<Stay> stays = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Maintenance> maintenances = new ArrayList<>();

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

    public void removeStay(Stay stay){
        stays.remove(stay);
        stay.setRoom(null);
    }

    public void addStay(Stay stay){
        stays.add(stay);
        stay.setRoom(this);
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public void removeMaintenance(Maintenance maintenance){
        maintenances.remove(maintenance);
        maintenance.setRoom(null);
    }

    public void addMaintenance(Maintenance maintenance){
        maintenances.add(maintenance);
        maintenance.setRoom(this);
    }


    //Evitar que a JPA tente mapear esse "CurrentStay" como uma coluna em Room no banco.
    // Serve só para usar no thymeleaf pra capturar o nome do hospede atual.
    @Transient
    public Stay getCurrentStay() {
        return stays.stream()
                .filter(x -> x.getStatus() == StayStatusEnum.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Transient
    public LocalDateTime getCheckOutPrediction(){
        var stay = getCurrentStay();
        var stayAmount = stays.getLast().getStayAmount();
        return stay.getCheckIn().plusDays(stayAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return getId().equals(room.getId())
                && getNumber().equals(room.getNumber())
                && getSingleBed().equals(room.getSingleBed())
                && getDoubleBed().equals(room.getDoubleBed())
                && getStatus() == room.getStatus();
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
