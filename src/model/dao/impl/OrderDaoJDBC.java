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
import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dao.OrderDao;
import model.dao.ProductDao;
import model.entities.Customer;
import model.entities.Order;
import model.entities.OrderItem;
import model.entities.Product;
import model.enums.OrderStatus;

public class OrderDaoJDBC implements OrderDao {
	
	private Connection conn;
	
	public OrderDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Order obj) {
		PreparedStatement pst = null;

		try {

			String sql = "INSERT INTO `order` (Moment, Status, CustomerId) VALUES (?, ?, ?)";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setTimestamp(1, new java.sql.Timestamp(obj.getMoment().getTime()));
			pst.setString(2, obj.getStatus().name());
			pst.setInt(3, obj.getCustomer().getId());

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

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public void update(Order obj) {
		PreparedStatement pst = null;

		try {

			String sql = "UPDATE `order` SET Moment=?, Status=?, CustomerId=? WHERE Id = ?";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setTimestamp(1, new java.sql.Timestamp(obj.getMoment().getTime()));
			pst.setString(2, obj.getStatus().name());
			pst.setInt(3, obj.getCustomer().getId());
			
			pst.setInt(4, obj.getId());

			pst.executeUpdate();
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

			String sql = "DELETE FROM `order` WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("The order ID does not exist in the database.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public Order findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pstItems = null;
		ResultSet rsItems = null;

		try {

			String sql = "SELECT * FROM `order` WHERE Id = ?";
			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {

				Order order = instantiateOrder(rs);

				pstItems = conn.prepareStatement(
						"SELECT * FROM order_item WHERE OrderId = ?");

				pstItems.setInt(1, order.getId());
				rsItems = pstItems.executeQuery();

				while (rsItems.next()) {
					OrderItem item = instantiateOrderItem(rsItems);
					order.getItems().add(item);
				}

				DB.closeStatement(pstItems);
				DB.closeResultSet(rsItems);

				return order;

			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
			DB.closeStatement(pstItems);
			DB.closeResultSet(rsItems);
		}
	}
	
	private Order instantiateOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt("Id"));
		order.setMoment(rs.getTimestamp("Moment"));
		order.setStatus(OrderStatus.valueOf(rs.getString("Status")));
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		Customer customer = customerDao.findById(rs.getInt("CustomerId"));
		order.setCustomer(customer);
		return order;
	}
	
	private OrderItem instantiateOrderItem(ResultSet rs) throws SQLException {

		OrderItem item = new OrderItem();

		item.setQuantity(rs.getInt("Quantity"));
		item.setPrice(rs.getDouble("Price"));

		ProductDao productDao = DaoFactory.createProductDao();
		Product product = productDao.findById(rs.getInt("ProductId"));

		item.setProduct(product);

		return item;
	}

	@Override
	public List<Order> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM `order`";

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			List<Order> list = new ArrayList<>();

			while (rs.next()) {

				Order order = instantiateOrder(rs);
				list.add(order);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Order> findByCustomer(Customer customer) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM `order` WHERE CustomerId=?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, customer.getId());
			rs = pst.executeQuery();

			List<Order> list = new ArrayList<>();

			while (rs.next()) {
				Order obj = instantiateOrder(rs);
				list.add(obj);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void updateStatus(Integer orderId, OrderStatus status) {
		PreparedStatement pst = null;

		try {

			String sql = "UPDATE `order` SET Status=? WHERE Id = ?";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, status.name());
			pst.setInt(2, orderId);

			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

}
