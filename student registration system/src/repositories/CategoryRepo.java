package repositories;

import java.util.List;

import entities.Category;

public interface CategoryRepo {
    void saveCategory(String id,Category category);

    void updateCategory(String id, Category category);

    List<Category> findAllCategories();

    Category findById(String id);

    void deleteCategory(String id);
    String getAutoId(String field,String prefix);
}
