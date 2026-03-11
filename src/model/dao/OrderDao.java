package model.dao;

import java.util.List;

import model.entities.Customer;
import model.entities.Order;
import model.enums.OrderStatus;

public interface OrderDao {
	
	void insert(Order obj);
	void update(Order obj);
	void deleteById(Integer id);
	Order findById(Integer id);
	List<Order> findAll();
	Order findByCustumer(Customer customer);
	void updateStatus(Integer orderId, OrderStatus status);
	
}
