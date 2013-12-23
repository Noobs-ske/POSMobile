package domain;

import java.util.ArrayList;

public class Sale {
	private double total = 0;
	private static Sale sale = null;
	private static ArrayList<SaleListItem> productList = null;

	public Sale() {
		checkInstance();
	}

	public static void checkInstance() {
		if (sale == null)
			sale = new Sale();
		if (productList == null)
			productList = new ArrayList<SaleListItem>();
	}

	public void addItem(SaleListItem item) {

		productList.add(item);

	}

	public void removeItem(SaleListItem item) {
		if (productList.contains(item))
			productList.remove(item);
	}
}
