package service.custom.impl;

import dao.DaoFactory;
import dao.custom.CategoryDao;
import dto.CategoryDto;
import entity.CategoryEntity;
import org.hibernate.Transaction;
import service.custom.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = (CategoryDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.CATEGORY);

    @Override
    public String saveCategory(CategoryDto categoryDto) {
        Transaction transaction = session.beginTransaction();

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategory(categoryDto.getCategory());
        categoryEntity.setCreatedBy(categoryDto.getCreatedBy());

        if(categoryDao.save(categoryEntity, session)) {
            transaction.commit();
            return "Saved Successfully";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }
    @Override
    public String updateCategory(CategoryDto categoryDto, String previousCategory) {
        Transaction transaction = session.beginTransaction();
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategory(categoryDto.getCategory());
        categoryEntity.setCreatedBy(categoryDto.getCreatedBy());
        categoryEntity.setUpdatedBy(categoryDto.getUpdatedBy());

        if(categoryDao.update(categoryEntity, session, previousCategory)) {
            transaction.commit();
            return "Updated Successfully";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public String deleteCategory(String category) {
        Transaction transaction = session.beginTransaction();
        if(categoryDao.delete(category, session)) {
            transaction.commit();
            return "Deleted Successfully";
        } else {
            transaction.rollback();
            return "Failed";
        }
    }

    @Override
    public CategoryDto getCategory(String category) {
        CategoryEntity categoryEntity = categoryDao.get(category, session);
        return new CategoryDto(
                categoryEntity.getCategory(),
                categoryEntity.getCreatedBy(),
                categoryEntity.getUpdatedBy()
        );
    }
    @Override
    public List<CategoryDto> getAllCategories() {
        ArrayList<CategoryDto> categoryDtos = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryDao.getAll(session);

        for(CategoryEntity categoryEntity : categoryEntities) {
            CategoryDto categoryDto = new CategoryDto(
                    categoryEntity.getCategory(),
                    categoryEntity.getCreatedBy(),
                    categoryEntity.getUpdatedBy()
            );
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }
}
