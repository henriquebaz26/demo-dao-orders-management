package model.dao;

import java.util.List;

import model.entities.Customer;

public interface CustomerDao {
	
	void insert(Customer obj);
	void update(Customer obj);
	void deleteById(Integer id);
	Customer findById(Integer id);
	List<Customer> findAll();
	Customer findByEmail(Customer customer);
	
}
