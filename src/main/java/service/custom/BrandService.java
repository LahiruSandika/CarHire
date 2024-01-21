package service.custom;

import dto.BrandDto;
import service.SuperService;

import java.util.List;

public interface BrandService extends SuperService {
    String saveBrand(BrandDto brandDto);

    String updateBrand(BrandDto brandDto, String previousBrand);

    String deleteBrand(String brand);

    BrandDto getBrand(String brand);

    List<BrandDto> getAllBrands();
}
