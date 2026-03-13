package model.dao;

import model.entities.Order;
import model.entities.OrderItem;

public interface OrderItemDao {
	
	void insert(OrderItem obj);
	void deleteById(Integer id);
	OrderItem findByOrder(Order order);
	
}
