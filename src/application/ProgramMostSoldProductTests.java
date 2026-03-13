package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.dto.MostSoldProduct;

public class ProgramMostSoldProductTests {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== TESTE 1: product mostSoldProduct ===");
		ProductDao productDao = DaoFactory.createProductDao();
		List<MostSoldProduct> list = productDao.mostSoldProduct();
		for (MostSoldProduct obj : list) {
			System.out.println(obj);
		}
		
		sc.close();

	}

}
