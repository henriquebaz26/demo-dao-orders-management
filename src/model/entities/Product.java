package model.entities;

import java.io.Serializable;
import java.util.Objects;

import db.DbException;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer stock;
	
	private Category category;
	
	public Product() {
	}

	public Product(Integer id, String name, String description, Double price, Integer stock, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void decrementStock(int quantity) {
	    if (quantity <= 0) {
	        throw new DbException("Quantity must be positive");
	    }
	    if (stock < quantity) {
	        throw new DbException("Not enough stock to decrement");
	    }
	    this.stock -= quantity;
	}
	
	public void incrementStock(int quantity) {
	    if (quantity <= 0) {
	        throw new DbException("Quantity must be positive");
	    }
	    this.stock += quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock="
				+ stock + ", categoryId=" + category + "]";
	}

}
