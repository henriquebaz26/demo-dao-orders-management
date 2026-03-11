package model.dao;

import model.entities.OrderItem;

public interface OrderItemDao {
	
	void insert(OrderDao obj);
	void deleteById(Integer id);
	OrderItem findByOrder(OrderDao order);
	
}
