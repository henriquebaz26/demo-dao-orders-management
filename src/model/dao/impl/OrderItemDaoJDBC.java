package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class OrderItemDaoJDBC implements OrderItemDao {
	
	ProductDao productDao = DaoFactory.createProductDao();
	
	private Connection conn;
	
	public OrderItemDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(OrderItem obj) {
		PreparedStatement pst = null;

		try {

			String sql = "INSERT INTO order_item (Quantity, Price, OrderId, ProductId) VALUES (?, ?, ?, ?)";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, obj.getQuantity());
			pst.setDouble(2, obj.getPrice());
			pst.setInt(3, obj.getOrder().getId());
			pst.setInt(4, obj.getProduct().getId());

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
					
				Product product = productDao.findById(obj.getProduct().getId());
				product.decrementStock(obj.getQuantity());
				productDao.update(product);
					
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;

		try {
			
			String sql = "SELECT * FROM order_item WHERE Id = ?";
			
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
			    Product product = productDao.findById(rs.getInt("ProductId"));
			    product.incrementStock(rs.getInt("Quantity"));
			    productDao.update(product);
			} else {
			    throw new DbException("The order item ID does not exist in the database.");
			}
			
			sql = "DELETE FROM order_item WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("The order item ID does not exist in the database.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public List<OrderItem> findByOrder(Order order) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM order_item WHERE OrderId=?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, order.getId());
			rs = pst.executeQuery();
			
			List<OrderItem> list = new ArrayList<>();

			while (rs.next()) {
				OrderItem item = instantiateOrderItem(rs, order);
				list.add(item);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	private OrderItem instantiateOrderItem(ResultSet rs, Order order) throws SQLException {
	    OrderItem item = new OrderItem();
	    item.setId(rs.getInt("Id"));
	    item.setQuantity(rs.getInt("Quantity"));
	    item.setPrice(rs.getDouble("Price"));
	    item.setOrder(order);
	    ProductDao productDao = DaoFactory.createProductDao();
	    Product product = productDao.findById(rs.getInt("ProductId"));
	    item.setProduct(product);
	    return item;
	}

}
