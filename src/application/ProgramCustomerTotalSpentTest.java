package application;

import java.util.List;
import java.util.Locale;

import model.dao.CustomerDao;
import model.dao.DaoFactory;
import model.dto.CustomerTotalSpent;

public class ProgramCustomerTotalSpentTest {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		System.out.println("\n=== TESTE 1: customer total spent test ===");
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		List<CustomerTotalSpent> list = customerDao.totalSpentByCustomer();

		for (CustomerTotalSpent x : list) {
		    System.out.println(x.getName() + " - " + String.format("%.2f", x.getTotalSpent()));
		}
		
	}
	
}
