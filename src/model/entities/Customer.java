package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String email;
	private String phone;
	private Date createAt;
	
	public Customer() {
	}

	public Customer(Integer id, String name, String email, String phone, Date createAt) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.createAt = createAt;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
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
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", createAt="
				+ createAt + "]";
	}
	
}
