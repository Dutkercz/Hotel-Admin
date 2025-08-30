package hotel_admin.dutkercz.com.github.dtos;

import hotel_admin.dutkercz.com.github.model.StayGuest;

import java.util.List;

public record StayRequestDTO(
        Long clientId,
        Long roomId,
        List<StayGuest> guests
) {
}
