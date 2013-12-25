package com.example.pointofsale;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dao.InventoryDB;
import dao.SaleReportDB;
import domain.SaleListItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SaveSaleActivity extends Activity{
ArrayList<SaleListItem> reportList;
ArrayList<SaleListItem> purchaseList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ok);
		Intent intent = this.getIntent();
	  	reportList = intent.getParcelableArrayListExtra("ReportList");
	  	purchaseList = intent.getParcelableArrayListExtra("PurchaseList");
	  	SaveSale(reportList);
	  	
	  	Intent newActivity = new Intent(SaveSaleActivity.this,
				ProductCatalogActivity.class);
		newActivity.putParcelableArrayListExtra("PurchaseList",
				purchaseList);
	  	
		startActivity(newActivity);
		finish();
	}
	
	@SuppressLint("SimpleDateFormat")
	public void SaveSale(ArrayList<SaleListItem> reportList)
	{
		final SaleReportDB myDb = new SaleReportDB(this);
		ArrayList<SaleListItem> reportList2 = reportList;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat asf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String now = asf.format(c.getTime());
		
		for(int i = 0; i < reportList2.size(); i++)
		{
			SaleListItem product = reportList2.get(i);
			String ID = product.getProductID();
			String Name = product.getProductName();
			int quan = product.getProductQuan();
			int price = product.getProductPrice();
			int total = price*quan;
			String now2 = "On: " + now+ ", Sold: " + quan + " " + Name + " for " + price + " each for a total of " + total + " baht"; 
			myDb.InsertData(now2, Name, Integer.toString(quan), Integer.toString(price));
		}
		reportList = new ArrayList<SaleListItem>();
	}

}
