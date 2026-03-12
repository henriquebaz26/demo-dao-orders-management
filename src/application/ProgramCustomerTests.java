package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.entities.Customer;

public class ProgramCustomerTests {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== TESTE 1: customer findById ===");
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		Customer customer = customerDao.findById(1);
		System.out.println(customer);
		
		System.out.println("\n=== TESTE 2: customer findByEmail ===");
		String email = "henrique@gmail.com";
		customer = customerDao.findByEmail(email);
		System.out.println(customer);
		
		System.out.println("\n=== TESTE 3: customer findAll ===");
		List<Customer> list = customerDao.findAll();
		for (Customer obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 4: customer insert ===");
		Customer newCustomer = new Customer(null, "Caroline Mueller", "caroline@gmail.com", "9999-9999", new Date());
		customerDao.insert(newCustomer);
		System.out.println("Inserted! New id = " + newCustomer.getId());
		
		System.out.println("\n=== TESTE 5: customer update ===");
		customer = customerDao.findById(2);
		customer.setName("Caroline Baz");
		customerDao.update(customer);
		System.out.println("Update Completed!");
		
		System.out.println("\n=== TESTE 6: customer delete ===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		customerDao.deleteById(id);
		System.out.println("Delete Completed!");
		
		sc.close();

	}

}
