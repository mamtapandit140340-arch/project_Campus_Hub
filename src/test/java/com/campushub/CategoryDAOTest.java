package com.campushub;

import com.campushub.dao.CategoryDAO;
import com.campushub.exception.DatabaseException;
import com.campushub.model.Category;

import java.util.List;

public class CategoryDAOTest {

    public static void main(String[] args) {

        CategoryDAO dao = new CategoryDAO();

        try {

            // =====================================================
            // CREATE
            // =====================================================

            System.out.println("========== CREATE ==========");

            // Unique name every time the program runs
            String categoryName =
                    "Test Category " + System.currentTimeMillis();

            Category category = new Category(
                    categoryName,
                    "Category created for DAO testing"
            );

            boolean added = dao.addCategory(category);

            System.out.println("Category added: " + added);
            System.out.println("Category Name: " + categoryName);


            // =====================================================
            // FIND CREATED CATEGORY
            // =====================================================

            int categoryId = 0;

            if (added) {

                List<Category> categories = dao.getAllCategories();

                for (Category c : categories) {

                    if (c.getCatName().equals(categoryName)) {

                        categoryId = c.getId();
                        break;
                    }
                }
            }

            if (categoryId == 0) {

                System.out.println(
                        "Could not find the newly created category."
                );

                return;
            }

            System.out.println("Generated Category ID: " + categoryId);


            // =====================================================
            // READ ONE
            // =====================================================

            System.out.println("\n========== READ ONE ==========");

            Category foundCategory =
                    dao.getCategoryById(categoryId);

            if (foundCategory != null) {

                System.out.println("Category found!");
                System.out.println(
                        "ID: " + foundCategory.getId()
                );
                System.out.println(
                        "Name: " + foundCategory.getCatName()
                );
                System.out.println(
                        "Description: " +
                                foundCategory.getDescription()
                );

            } else {

                System.out.println("Category not found.");
            }


            // =====================================================
            // READ ALL
            // =====================================================

            System.out.println("\n========== READ ALL ==========");

            List<Category> allCategories =
                    dao.getAllCategories();

            System.out.println(
                    "Total categories: " + allCategories.size()
            );

            for (Category c : allCategories) {

                System.out.println(
                        "ID: " + c.getId()
                                + " | Name: " + c.getCatName()
                                + " | Description: "
                                + c.getDescription()
                );
            }


            // =====================================================
            // UPDATE
            // =====================================================

            System.out.println("\n========== UPDATE ==========");

            foundCategory.setCatName(
                    categoryName + " Updated"
            );

            foundCategory.setDescription(
                    "Updated category description"
            );

            boolean updated =
                    dao.updateCategory(foundCategory);

            System.out.println(
                    "Category updated: " + updated
            );


            // =====================================================
            // READ AFTER UPDATE
            // =====================================================

            System.out.println(
                    "\n========== READ AFTER UPDATE =========="
            );

            Category updatedCategory =
                    dao.getCategoryById(categoryId);

            if (updatedCategory != null) {

                System.out.println(
                        "ID: " + updatedCategory.getId()
                );

                System.out.println(
                        "Updated Name: " +
                                updatedCategory.getCatName()
                );

                System.out.println(
                        "Updated Description: " +
                                updatedCategory.getDescription()
                );
            }


            // =====================================================
            // DELETE
            // =====================================================

            System.out.println("\n========== DELETE ==========");

            boolean deleted =
                    dao.deleteCategory(categoryId);

            System.out.println(
                    "Category deleted: " + deleted
            );


            // =====================================================
            // VERIFY DELETE
            // =====================================================

            System.out.println(
                    "\n========== VERIFY DELETE =========="
            );

            Category deletedCategory =
                    dao.getCategoryById(categoryId);

            if (deletedCategory == null) {

                System.out.println(
                        "Category deleted successfully."
                );

            } else {

                System.out.println(
                        "Category was not deleted."
                );
            }


            // =====================================================
            // TEST COMPLETE
            // =====================================================

            System.out.println(
                    "\n========== TEST COMPLETED =========="
            );

        } catch (DatabaseException e) {

            System.out.println(
                    "\n========== DATABASE ERROR =========="
            );

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println(
                    "\n========== ERROR =========="
            );

            e.printStackTrace();
        }
    }
}