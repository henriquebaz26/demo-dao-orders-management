package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class ProgramOrderItemTests {
	
public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);	
		
		System.out.println("=== TESTE 1: orderItem findByOrder ===");
		OrderItemDao orderItemDao = DaoFactory.createOrderItemDao();
		OrderDao orderDao = DaoFactory.createOrderDao();
		Order order = orderDao.findById(2);
		List<OrderItem> list = orderItemDao.findByOrder(order);
		System.out.println(list);
		
		System.out.println("\n=== TESTE 2: orderItem insert ===");
		ProductDao productDao = DaoFactory.createProductDao();
		Product product = productDao.findById(5);
		int quantity = 10;
		OrderItem newOrderItem = new OrderItem(null, quantity, product.getPrice(), order, product);
		orderItemDao.insert(newOrderItem);
		System.out.println("Inserted! New id = " + newOrderItem.getId());
		
		System.out.println("\n=== TESTE 3: orderItem delete ===");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		orderItemDao.deleteById(id);
		System.out.println("Delete Completed!");
		
		sc.close();

	}
	
}
