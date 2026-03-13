package model.dto;

public class CustomerTotalSpent {
	
	private Integer customerId;
    private String name;
    private Double totalSpent;
    
    public CustomerTotalSpent() {
    }

	public CustomerTotalSpent(Integer customerId, String name, Double totalSpent) {
		this.customerId = customerId;
		this.name = name;
		this.totalSpent = totalSpent;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}
    
}
