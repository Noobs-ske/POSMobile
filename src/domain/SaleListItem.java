package domain;

public class SaleListItem {
	
	private String productName;
	private int productQuan;
	private int productPrice;
	private String productID;
	
	public SaleListItem(String productName, int productQuan,
			int productPrice,String productID) 
	{
		this.productID = productID;
		this.productName = productName;
		this.productQuan = productQuan;
		this.productPrice = productPrice;
	}


	public String getProductID() {

		return productID;
	}

	public void setProductID(String Productid) {

		 this.productID = Productid;
	}
	
	public String getProductName() {

		return productName;
	}

	public void setProductName(String Productname) {

		 this.productName = Productname;
	}
	
	public int getProductQuan() {

		return productQuan;
	}
	
	public void setProductQuan(int ProductQuan) {

		this.productQuan = ProductQuan;
	}

	public int getProductPrice() {

		return productPrice;
	}
	public void setProductPrice(int ProductPrice) {

		 this.productPrice = ProductPrice;
	}
	
}
