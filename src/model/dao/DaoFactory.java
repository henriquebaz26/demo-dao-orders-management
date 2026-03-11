package model.dao;

import db.DB;

public class DaoFactory {
	
	public static CustomerDao createCustomerDao() {
		return new CostumerDaoJDBC(DB.getConnection());
	}
	
	public static CategoryDao createCategoryDao() {
		return new CategoryDaoJDBC(DB.getConnection());
	}
	
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
	
	public static OrderDao createOrderDao() {
		return new OrderDaoJDBC(DB.getConnection());
	}
	
	public static OrderItemDao createOrderItemDao() {
		return new OrderItemDaoJDBC(DB.getConnection());
	}
	
}
