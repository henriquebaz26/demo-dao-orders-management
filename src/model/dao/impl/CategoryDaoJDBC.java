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
import model.dao.CategoryDao;
import model.dto.TotalRevenueCategory;
import model.entities.Category;

public class CategoryDaoJDBC implements CategoryDao {
	
	private Connection conn;
	
	public CategoryDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Category obj) {
		PreparedStatement pst = null;

		try {

			String sql = "INSERT INTO category (Name) VALUES (?)";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());

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
	public void update(Category obj) {
		PreparedStatement pst = null;

		try {

			String sql = "UPDATE category SET Name = ? WHERE Id = ?";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			
			pst.setInt(2, obj.getId());

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

			String sql = "DELETE FROM category WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("The category ID does not exist in the database.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public Category findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM category WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				Category category = instantiateCategory(rs);
				return category;

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
	
	private Category instantiateCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt("Id"));
		category.setName(rs.getString("Name"));
		return category;
	}

	@Override
	public List<Category> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM category";

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			List<Category> list = new ArrayList<>();

			while (rs.next()) {

				Category category = instantiateCategory(rs);
				list.add(category);

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
	public List<TotalRevenueCategory> totalRevenueCategory() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT \r\n"
					+ "    c.id AS category_id,\r\n"
					+ "    c.name AS category_name,\r\n"
					+ "    SUM(oi.quantity * oi.price) AS total_revenue\r\n"
					+ "FROM category c\r\n"
					+ "JOIN product p ON p.CategoryId = c.id\r\n"
					+ "JOIN order_item oi ON oi.ProductId = p.id\r\n"
					+ "GROUP BY c.id, c.name\r\n"
					+ "ORDER BY total_revenue DESC LIMIT 3;";

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			List<TotalRevenueCategory> list = new ArrayList<>();

			while (rs.next()) {

				TotalRevenueCategory category = instantiateTotalRevenueCategory(rs);
				list.add(category);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	private TotalRevenueCategory instantiateTotalRevenueCategory(ResultSet rs) throws SQLException {
		TotalRevenueCategory category = new TotalRevenueCategory();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		category.setTotalRevenue(rs.getDouble("total_revenue"));
		return category;
	}

}
