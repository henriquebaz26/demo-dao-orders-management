package model.dao;

import java.util.List;

import model.entities.Order;
import model.entities.OrderItem;

public interface OrderItemDao {
	
	void insert(OrderItem obj);
	void deleteById(Integer id);
	List<OrderItem> findByOrder(Order order);
	
}
