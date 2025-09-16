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

    @ManyToOne
    private Room room;

    public Maintenance() {
    }

    public Maintenance(Long id, LocalDateTime startMaintenance, LocalDateTime endMaintenance, Room room) {
        this.id = id;
        this.startMaintenance = startMaintenance;
        this.endMaintenance = endMaintenance;
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
}
