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
import model.dto.CustomerTotalSpent;
import model.entities.Customer;

public class CustomerDaoJDBC implements CustomerDao {
	
	private Connection conn;
	
	public CustomerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Customer obj) {
		PreparedStatement pst = null;

		try {

			String sql = "INSERT INTO customer (Name, Email, Phone, CreatedAt) VALUES (?, ?, ?, ?)";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setString(3, obj.getPhone());
			pst.setDate(4, new java.sql.Date(obj.getCreateAt().getTime()));

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
	public void update(Customer obj) {
		PreparedStatement pst = null;

		try {

			String sql = "UPDATE customer SET Name = ?, Email=?, Phone=?, CreatedAt=? WHERE Id = ?";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setString(3, obj.getPhone());
			pst.setDate(4, new java.sql.Date(obj.getCreateAt().getTime()));
			
			pst.setInt(5, obj.getId());

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
			
			String sql = "SELECT * FROM `order` WHERE CustomerId = ?";
			
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				throw new DbException("Cannot delete customer: associated orders exist.");
			} else {
				sql = "DELETE FROM customer WHERE Id = ?";

				pst = conn.prepareStatement(sql);

				pst.setInt(1, id);

				int rowsAffected = pst.executeUpdate();

				if (rowsAffected == 0) {
					throw new DbException("The customer ID does not exist in the database.");
				}
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public Customer findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM customer WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				Customer customer = instantiateCustomer(rs);
				return customer;

			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	private Customer instantiateCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getInt("Id"));
		customer.setName(rs.getString("Name"));
		customer.setEmail(rs.getString("Email"));
		customer.setPhone(rs.getString("Phone"));
		customer.setCreateAt(rs.getDate("CreatedAt"));
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM customer";

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			List<Customer> list = new ArrayList<>();

			while (rs.next()) {

				Customer customer = instantiateCustomer(rs);
				list.add(customer);

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
	public Customer findByEmail(String email) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM customer WHERE Email=?";

			pst = conn.prepareStatement(sql);

			pst.setString(1, email);
			rs = pst.executeQuery();

			Customer cus = new Customer();

			if (rs.next()) {

				cus = instantiateCustomer(rs);

			}

			return cus;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<CustomerTotalSpent> totalSpentByCustomer() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			List<CustomerTotalSpent> list = new ArrayList<>();

		    String sql =
		        "SELECT c.Id, c.Name, SUM(oi.Quantity * oi.Price) AS TotalSpent " +
		        "FROM customer c " +
		        "JOIN `order` o ON o.CustomerId = c.Id " +
		        "JOIN order_item oi ON oi.OrderId = o.Id " +
		        "GROUP BY c.Id, c.Name";

		    pst = conn.prepareStatement(sql);
		    rs = pst.executeQuery();

		    while (rs.next()) {

		        CustomerTotalSpent dto = new CustomerTotalSpent();

		        dto.setCustomerId(rs.getInt("Id"));
		        dto.setName(rs.getString("Name"));
		        dto.setTotalSpent(rs.getDouble("TotalSpent"));

		        list.add(dto);
		    }

		    return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
}
