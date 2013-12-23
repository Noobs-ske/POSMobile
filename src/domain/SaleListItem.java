package domain;

import android.os.Parcel;
import android.os.Parcelable;

public class SaleListItem implements Parcelable {

	private String productID;
	private String productName;
	private int productQuan;
	private int productPrice;

	public SaleListItem(Parcel source) {
		productID = source.readString();
		productName = source.readString();
		productQuan = source.readInt();
		productPrice = source.readInt();
	}

	public SaleListItem(String productID, String productName, int productQuan, int productPrice
			) {
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

	@Override
	public int describeContents() {
		return this.hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(productID);
		dest.writeString(productName);
		dest.writeInt(productQuan);
		dest.writeInt(productPrice);

	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public SaleListItem createFromParcel(Parcel in) {
			return new SaleListItem(in);
		}

		public SaleListItem[] newArray(int size) {
			return new SaleListItem[size];
		}
	};

}
