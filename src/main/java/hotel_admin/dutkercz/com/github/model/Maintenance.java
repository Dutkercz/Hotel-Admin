package hotel_admin.dutkercz.com.github.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startMaintenance;
    private LocalDateTime endMaintenance;
    private Boolean isActive;

    @ManyToOne
    private Room room;

    public Maintenance() {
    }

    public Maintenance(Long id, LocalDateTime startMaintenance, LocalDateTime endMaintenance, Boolean isActive, Room room) {
        this.id = id;
        this.startMaintenance = startMaintenance;
        this.endMaintenance = endMaintenance;
        this.isActive = isActive;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartMaintenance() {
        return startMaintenance;
    }

    public void setStartMaintenance(LocalDateTime startMaintenance) {
        this.startMaintenance = startMaintenance;
    }

    public LocalDateTime getEndMaintenance() {
        return endMaintenance;
    }

    public void setEndMaintenance(LocalDateTime endMaintenance) {
        this.endMaintenance = endMaintenance;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
