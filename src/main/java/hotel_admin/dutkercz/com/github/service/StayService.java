package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.repository.StayRepository;
import org.springframework.stereotype.Service;

@Service
public class StayService {

    private final StayRepository stayRepository;


    public StayService(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
    }


    public void saveStay(Stay stay) {

    }
}
