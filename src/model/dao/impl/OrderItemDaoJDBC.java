package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.OrderItemDao;
import model.dao.ProductDao;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;

public class OrderItemDaoJDBC implements OrderItemDao {
	
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
			
			if (obj.getProduct().getStock() < obj.getQuantity()) {
				throw new DbException("Insufficient stock for the requested quantity.");
			} else {
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
					DB.closeResultSet(rs);
				} else {
					throw new DbException("Unexpected error! No rows affected");
				}
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

			String sql = "DELETE FROM order_item WHERE Id = ?";

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
	public OrderItem findByOrder(Order order) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM order_item WHERE OrderId=?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, order.getId());
			rs = pst.executeQuery();
			
			OrderItem orderItem = new OrderItem();

			while (rs.next()) {
				orderItem = instantiateOrderItem(rs);
			}
			
			return orderItem;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	private OrderItem instantiateOrderItem(ResultSet rs) throws SQLException {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(Integer.parseInt(rs.getString("Id")));
		orderItem.setQuantity(Integer.parseInt(rs.getString("Quantity")));
		orderItem.setPrice(Double.parseDouble(rs.getString("Price")));
		OrderDao orderDao = DaoFactory.createOrderDao();
		Order order = orderDao.findById(Integer.parseInt(rs.getString("OrderId")));
		orderItem.setOrder(order);
		ProductDao productDao = DaoFactory.createProductDao();
		Product product = productDao.findById(Integer.parseInt(rs.getString("ProductId")));
		orderItem.setProduct(product);
		return orderItem;
	}

}
