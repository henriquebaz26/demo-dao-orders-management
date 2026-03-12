package application;

import java.util.List;
import java.util.Scanner;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.entities.Category;

public class ProgramCategoryTests {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== TESTE 1: category findById ===");
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		Category category = categoryDao.findById(1);
		System.out.println(category);
		
		System.out.println("\n=== TESTE 2: category findAll ===");
		List<Category> list = categoryDao.findAll();
		for (Category obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 3: category insert ===");
		Category newCategory = new Category(null, "Tecnologia");
		categoryDao.insert(newCategory);
		System.out.println("Inserted! New id = " + newCategory.getId());
		
		System.out.println("\n=== TESTE 4: category update ===");
		category = categoryDao.findById(1);
		category.setName("Casa");
		categoryDao.update(category);
		System.out.println("Update Completed!");
		
		System.out.println("\n=== TESTE 5: category delete ===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		categoryDao.deleteById(id);
		System.out.println("Delete Completed!");
		
		
		sc.close();

	}

}
