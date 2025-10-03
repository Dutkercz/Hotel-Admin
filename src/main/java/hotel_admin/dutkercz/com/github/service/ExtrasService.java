package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Extras;
import hotel_admin.dutkercz.com.github.repository.ExtrasRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ExtrasService {

    private final ExtrasRepository extrasRepository;

    public ExtrasService(ExtrasRepository extrasRepository) {
        this.extrasRepository = extrasRepository;
    }

    @Transactional
    public void saveExtras(Extras extras){
        extrasRepository.save(extras);
    }
}
