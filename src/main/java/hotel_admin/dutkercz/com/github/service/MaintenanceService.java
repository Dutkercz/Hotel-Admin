package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Maintenance;
import hotel_admin.dutkercz.com.github.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public List<Maintenance> findByDate(LocalDateTime todayDate) {
        return maintenanceRepository.findAllByStartMaintenanceBetween(todayDate, todayDate.plusDays(1));
    }
}
