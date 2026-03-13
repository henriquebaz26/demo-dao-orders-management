package application;

import java.util.List;
import java.util.Scanner;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dto.TotalRevenueCategory;

public class ProgramTotalRevenueCategoryTests {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=== TESTE 1: category totalRevenueCategory ===");
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		List<TotalRevenueCategory> list = categoryDao.totalRevenueCategory();
		for (TotalRevenueCategory obj : list) {
			System.out.println(obj);
		}
		
		sc.close();

	}

}
