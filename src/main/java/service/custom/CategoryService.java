package service.custom;

import dto.CategoryDto;
import service.SuperService;

import java.util.List;

public interface CategoryService extends SuperService {
    String saveCategory(CategoryDto categoryDto);

    String updateCategory(CategoryDto categoryDto, String previousCategory);

    String deleteCategory(String category);

    CategoryDto getCategory(String category);

    List<CategoryDto> getAllCategories();
}
