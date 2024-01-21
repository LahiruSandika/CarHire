package service.custom;

import dto.RentDto;
import service.SuperService;

import java.util.List;

public interface RentService extends SuperService {
    String saveRent(RentDto rentDto);

    String updateRent(RentDto rentDto);

    String deleteRent(String id);

    RentDto getRent(String id);

    List<RentDto> getAllRents();
}
