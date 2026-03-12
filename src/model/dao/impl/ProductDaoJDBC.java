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
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao {
	
	private Connection conn;
	
	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Product obj) {
		PreparedStatement pst = null;

		try {

			String sql = "INSERT INTO product (Name, Description, Price, Stock, CategoryId) VALUES (?, ?, ?, ?, ?)";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			pst.setString(2, obj.getDescription());
			pst.setDouble(3, obj.getPrice());
			pst.setInt(4, obj.getStock());
			pst.setInt(5, obj.getCategory().getId());

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
	public void update(Product obj) {
		PreparedStatement pst = null;

		try {

			String sql = "UPDATE product SET Name = ?, Description=?, Price=?, Stock=?, CategoryId=? WHERE Id = ?";

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, obj.getName());
			pst.setString(2, obj.getDescription());
			pst.setDouble(3, obj.getPrice());
			pst.setInt(4, obj.getStock());
			pst.setInt(5, obj.getCategory().getId());
			
			pst.setInt(6, obj.getId());

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

			String sql = "DELETE FROM product WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("The product ID does not exist in the database.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public Product findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM product WHERE Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				Product product = instantiateProduct(rs);
				return product;

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
	
	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("Id"));
		product.setName(rs.getString("Name"));
		product.setDescription(rs.getString("Description"));
		product.setPrice(Double.parseDouble(rs.getString("Price")));
		product.setStock(Integer.parseInt(rs.getString("Stock")));
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		Category category = categoryDao.findById(Integer.parseInt(rs.getString("CategoryId")));
		product.setCategory(category);
		return product;
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM product";

			pst = conn.prepareStatement(sql);

			rs = pst.executeQuery();

			List<Product> list = new ArrayList<>();

			while (rs.next()) {

				Product product = instantiateProduct(rs);
				list.add(product);

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
	public List<Product> findByCategory(Category category) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM product WHERE CategoryId=?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, category.getId());
			rs = pst.executeQuery();

			List<Product> list = new ArrayList<>();

			while (rs.next()) {
				Product obj = instantiateProduct(rs);
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
	public List<Product> findLowStock(int threshold) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			String sql = "SELECT * FROM product WHERE Stock <= ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, threshold);
			rs = pst.executeQuery();

			List<Product> list = new ArrayList<>();

			while (rs.next()) {
				Product obj = instantiateProduct(rs);
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

}
