package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Maintenance;
import hotel_admin.dutkercz.com.github.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public List<Maintenance> findAllMonthMaintenance() {
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atTime(12, 0);
        LocalDateTime endOfMonth = YearMonth.now().atEndOfMonth().atTime(11, 59);
        return maintenanceRepository.findAllByStartMaintenanceBetween(startOfMonth, endOfMonth);
    }
}
