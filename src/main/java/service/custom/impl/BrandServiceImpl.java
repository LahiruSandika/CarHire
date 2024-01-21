package service.custom.impl;

import dao.DaoFactory;
import dao.custom.BrandDao;
import dto.BrandDto;
import entity.BrandEntity;
import org.hibernate.Transaction;
import service.custom.BrandService;

import java.util.ArrayList;
import java.util.List;

public class BrandServiceImpl implements BrandService {
    BrandDao brandDao = (BrandDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BRAND);
    @Override
    public String saveBrand(BrandDto brandDto) {
        Transaction transaction = session.beginTransaction();
        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setBrand(brandDto.getBrand());
        brandEntity.setCreatedBy(brandDto.getCreatedBy());

        if(brandDao.save(brandEntity, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public String updateBrand(BrandDto brandDto, String previousBrand) {
        Transaction transaction = session.beginTransaction();
        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setBrand(brandDto.getBrand());
        brandEntity.setCreatedBy(brandDto.getCreatedBy());
        brandEntity.setUpdatedBy(brandDto.getUpdatedBy());

        if(brandDao.update(brandEntity, session, previousBrand)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public String deleteBrand(String brand) {
        Transaction transaction = session.beginTransaction();
        if(brandDao.delete(brand, session)) {
            transaction.commit();
            return "Successfully Saved";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public BrandDto getBrand(String brand) {
        BrandEntity BrandEntity = brandDao.get(brand, session);
        return new BrandDto(
                BrandEntity.getBrand(),
                BrandEntity.getCreatedBy(),
                BrandEntity.getUpdatedBy()
        );
    }
    @Override
    public List<BrandDto> getAllBrands() {
        ArrayList<BrandDto> brandDtos = new ArrayList<>();
        List<BrandEntity> brandEntities = brandDao.getAll(session);

        for(BrandEntity brandEntity : brandEntities) {
            BrandDto brandDto = new BrandDto(
                    brandEntity.getBrand(),
                    brandEntity.getCreatedBy(),
                    brandEntity.getUpdatedBy()
            );
            brandDtos.add(brandDto);
        }
        return brandDtos;
    }
}
