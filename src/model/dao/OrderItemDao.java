package model.dao;

import model.entities.OrderItem;

public interface OrderItemDao {
	
	void insert(Order obj);
	void deleteById(Integer id);
	OrderItem findByOrder(Order order);
	
}
