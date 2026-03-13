package model.dto;

public class TotalRevenueCategory {

	private Integer id;
	private String name;
	private Double totalRevenue;
	
	public TotalRevenueCategory() {
	}

	public TotalRevenueCategory(Integer id, String name, Double totalRevenue) {
		super();
		this.id = id;
		this.name = name;
		this.totalRevenue = totalRevenue;
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

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	@Override
	public String toString() {
		return "TotalRevenueCategory [id=" + id + ", name=" + name + ", totalRevenue=" + totalRevenue + "]";
	}

}
