package com.example.pointofsale;

import java.util.ArrayList;

import dao.InventoryDB;
import domain.SaleListItem;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatesaleScreen extends Activity {

	ArrayList<SaleListItem> purchaseList;
	SaleListItem item;
	String MemID;
	final InventoryDB myDb = new InventoryDB(this);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatesale);
		
		// Read var from Intent
		Intent intent = this.getIntent();
		
		purchaseList = intent.getParcelableArrayListExtra("PurchaseList");
		final String MemID = intent.getStringExtra("MemID");
		
		// Show Data
		ShowData(MemID);

		// btnSave (Save)
		final Button save = (Button) findViewById(R.id.btnSave);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				

				final TextView tItemID = (TextView) findViewById(R.id.txtItemID);
				final EditText tName = (EditText) findViewById(R.id.txtName);
				final EditText tQuantity = (EditText) findViewById(R.id.txtQuantity);
				final EditText tPrice = (EditText) findViewById(R.id.txtPrice);
				
				String quan = tQuantity.getText().toString();
				String price = tPrice.getText().toString();

				//Try to find the item that needs to be saved
				for(int i = 0; i < purchaseList.size(); i++)
				{
					if(purchaseList.get(i).getProductID().equals(MemID))
					{
						final String[] iteminfo = myDb.SelectData(MemID);
						
						if(Integer.parseInt(quan) <= Integer.parseInt(iteminfo[2])){
						purchaseList.get(i).setProductQuan(Integer.parseInt(quan));
						purchaseList.get(i).setProductPrice(Integer.parseInt(price));
						
						Toast.makeText(getBaseContext(),
								"Sale adjustment complete",
								Toast.LENGTH_LONG).show();
						
						Intent newActivity = new Intent(UpdatesaleScreen.this,
								SaleActivity.class);
						
						newActivity.putParcelableArrayListExtra("PurchaseList",
								purchaseList);
						
						startActivity(newActivity);
						finish();
						break;
						}
						
						else {Toast.makeText(getBaseContext(),
								"Not enough item in stock",
								Toast.LENGTH_LONG).show();
						break;}
							
					}
				}
			}
		});

		// btnCancel (Cancel)
		final Button cancel = (Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent newActivity = new Intent(UpdatesaleScreen.this,
						SaleActivity.class);
				newActivity.putParcelableArrayListExtra("PurchaseList",
						purchaseList);
				startActivity(newActivity);
				
			}
		});

	}

	public void ShowData(String MemID) {
	//	ArrayList<SaleListItem> purchaseList2 = purchaseList;

		
		final TextView tItemID = (TextView) findViewById(R.id.txtItemID);
		final EditText tName = (EditText) findViewById(R.id.txtName);
		final EditText tQuantity = (EditText) findViewById(R.id.txtQuantity);
		final EditText tPrice = (EditText) findViewById(R.id.txtPrice);
		
		
		for(int i = 0; i < purchaseList.size(); i++)
		{
			if(purchaseList.get(i).getProductID().equals(MemID))
			{
				item = purchaseList.get(i);
				break;
			}
		}

		// Show Data
			tItemID.setText(item.getProductID());
			tName.setText(item.getProductName());
			tQuantity.setText(Integer.toString(item.getProductQuan()));
			tPrice.setText(Integer.toString(item.getProductPrice()));

	}
}
