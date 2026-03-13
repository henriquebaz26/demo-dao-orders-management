package model.dto;

public class MostSoldProduct {
	
	private Integer id;
	private String name;
	private Double totalSold;
	
	public MostSoldProduct() {
	}

	public MostSoldProduct(Integer id, String name, Double totalSold) {
		super();
		this.id = id;
		this.name = name;
		this.totalSold = totalSold;
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

	public Double getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(Double totalSold) {
		this.totalSold = totalSold;
	}

	@Override
	public String toString() {
		return "MostSoldProduct [id=" + id + ", name=" + name + ", totalSold=" + totalSold + "]";
	}
	
}
