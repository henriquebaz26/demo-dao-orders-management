package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.enums.OrderStatus;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date moment;
	private OrderStatus status;
	
	private Customer customer;
	
	private List<OrderItem> items = new ArrayList<>();
	
	public Order() {
	}

	public Order(Integer id, Date moment, OrderStatus status, Customer customer) {
		this.id = id;
		this.moment = moment;
		this.status = status;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public List<OrderItem> getItems() {
        return items;
    }
	
	public void addItem(OrderItem item) {
		items.add(item);
	}

	public void removeItem(OrderItem item) {
		items.remove(item);
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
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Order [id=" + id 
				+ ", moment=" + moment 
				+ ", status=" + status 
				+ ", customer=" + customer.getName()
				+ ", items=" + items.size()
				+ "]";
	}	
	
}
