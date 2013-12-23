package controller;

import java.util.HashSet;
import java.util.Observer;
import java.util.Set;



import domain.ProductCatalog;
import domain.Sale;

public class SaleController {

	/** Need the product catalog for finding items by id */
	private ProductCatalog productCatalog;
	/** the current sale, if any */
	private Sale sale;
	/** Observers of the sale */
	private Set<Observer> saleobservers;
	/**
	 * @param args
	 */
	public SaleController(ProductCatalog productCatalog , Sale sale) {
		// The store initializes product catalog, 
		// so create a store and then get the catalog.
		
		//3.change argument to receiver dependency injection from main class @edit by tachin
		this.productCatalog = productCatalog;
		this.sale = sale;
		
		//productCatalog = (new Store()).getProductCatalog();
		saleobservers = new HashSet<Observer>();
	}

	
}
