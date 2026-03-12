package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;

public class ProgramProductTests {

	public static void main(String[] args) {
			
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== TESTE 1: product findById ===");
		ProductDao productDao = DaoFactory.createProductDao();
		Product product = productDao.findById(1);
		System.out.println(product);
		
		System.out.println("\n=== TESTE 2: product findByCategory ===");
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		Category category = categoryDao.findById(2);
		List<Product> list = new ArrayList<>();
		list = productDao.findByCategory(category);
		for (Product obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 3: product findAll ===");
		list = productDao.findAll();
		for (Product obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 4: product findLowStock ===");
		list = productDao.findLowStock(2);
		for (Product obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 5: product insert ===");
		Product newProduct = new Product(null, "Mouse", "mouse gamer", 200.00, 8, category);
		productDao.insert(newProduct);
		System.out.println("Inserted! New id = " + newProduct.getId());
		
		System.out.println("\n=== TESTE 6: product update ===");
		product = productDao.findById(2);
		product.setPrice(2000.00);;
		productDao.update(product);
		System.out.println("Update Completed!");
		
		System.out.println("\n=== TESTE 7: product delete ===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		productDao.deleteById(id);
		System.out.println("Delete Completed!");
		
		sc.close();

	}

}
