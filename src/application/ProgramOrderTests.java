package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.entities.Customer;
import model.entities.Order;
import model.enums.OrderStatus;

public class ProgramOrderTests {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);	
		
		System.out.println("=== TESTE 1: order findById ===");
		OrderDao orderDao = DaoFactory.createOrderDao();
		Order order = orderDao.findById(1);
		System.out.println(order);
		
		System.out.println("\n=== TESTE 2: order findByCustomer ===");
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		Customer customer = customerDao.findById(2);
		List<Order> list = orderDao.findByCustumer(customer);
		for (Order obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 3: order findAll ===");
		list = orderDao.findAll();
		for (Order obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 4: order insert ===");
		Order newOrder = new Order(null, new Date(), OrderStatus.CANCELED, customer);
		orderDao.insert(newOrder);
		System.out.println("Inserted! New id = " + newOrder.getId());
		
		System.out.println("\n=== TESTE 5: order update ===");
		order = orderDao.findById(1);
		order.setCustomer(customerDao.findById(2));
		orderDao.update(order);
		System.out.println("Update Completed!");
		
		System.out.println("\n=== TESTE 6: order delete ===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		orderDao.deleteById(id);
		System.out.println("Delete Completed!");
		
		System.out.println("\n=== TESTE 6: order updateStatus ===");
		orderDao.updateStatus(2, OrderStatus.CANCELED);
		System.out.println("Status updated successfully.");
		
		sc.close();

	}

}
